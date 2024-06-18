package dk.dtu.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LogicSolver {

// takes a normal board and solves and checks it. Returns true if it can be solved deterministically, i.e. no guesses are required to solve the sudoku
    public static boolean validCheck (int[][] board) {
        return verification(solver(board));
    }
    
// checks if the board is filled out
    public static boolean isDone (int[][] board) {
        return PuzzleGenerator.zeroCount(board) == 0;
    }

// Generates a unit (long[][]) for each rule of the sudoku, 0 = rows, 1 = columns, 2 = boxes.
    private static BigInteger[][][] boardUnits (BigInteger[][] board){
        int size = board.length;
        int boxSize = (int) Math.sqrt(board.length);
        BigInteger[][] unitsRow = new BigInteger[size][size];  // 3 layers, one for rows=0, one for columns=1, one for boxes=2
        BigInteger[][] unitsCol = new BigInteger[size][size];
        BigInteger[][] unitsBox = new BigInteger[size][size];

        BigInteger x;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if (board[i][j].bitCount() > 1) {
                    x = board[i][j];
                } else x = board[i][j].not();

                unitsRow[i][j] = x;                                                                 // the row units.
                unitsCol[j][i] = x;                                                                 // the col units.
                unitsBox[(j/boxSize + (i/boxSize)*boxSize)][((i - (i/boxSize * boxSize))*boxSize + (j - (j/boxSize * boxSize)))] = x;        // the box units.
            }
        }      
        return new BigInteger[][][] {unitsRow, unitsCol, unitsBox};
    }
    
// Takes an integer values, and represents that value as the index in a binary string, when "oneOrZero" = false (0). i.e. 1=0001, 2=0010, 3=0100, ... - works for up to 63 bits.
// When "oneOrZero" = true (1), a string of 1's the size of the board length is created, this is used for the empty fields on the board.
    private static BigInteger valToBinary (int num, boolean ones) {
        BigInteger x = BigInteger.ZERO;
        if (ones) {
            for (int i = 0; i < num; i++) {
                x = x.setBit(i);
            }    
        } else {
            x = x.setBit(num-1);
        }
        return x;
    }

// Takes the original board, and creates a copy of where each cell is defined by a binary value.
    private static BigInteger[][] mirrorBoard (int[][] board) {
        int size = board.length;
        BigInteger[][] binaryBoard = new BigInteger[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    binaryBoard[i][j] = valToBinary(size, true);
                } else {
                    binaryBoard[i][j] = valToBinary(board[i][j], false);
                }
            }
        }
        return binaryBoard;
    }
    
// can descrcibe each square as a triple (rows, columns, box), row = i, col = j, box = (i/boxSize)*boxSize + j/boxSize)
    private static BigInteger[][][] solver (int[][] boardInput) {
        BigInteger[][] board = mirrorBoard(boardInput);
        int size = board.length;
        BigInteger[][][] units = boardUnits(board);
        List<int[]> priority = new ArrayList<int[]>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int[] t = {row,col};
                if (board[row][col].bitCount() > 1) {
                    priority.add(t);
                }        
            }
        }
        
        int cnt = 0;
        int prioritySizePrev = priority.size();
        int prioritySizeNow = priority.size();
        while (priority.size() > 0) {
            prioritySizePrev = priority.size();
            if (cnt == 0) {
                nakedSingles(units, board, priority);
            } else if (cnt == 1) {
                hiddenSingles(units, board, priority);
            } else break;
            // Can be expanded with more strategies.
            
            prioritySizeNow = priority.size();
            if (prioritySizeNow != prioritySizePrev) {
                cnt = 0;
            } else cnt++;
            System.out.println(priority.size());
        }
        return units;
    }

    private static void nakedSingles (BigInteger[][][] units, BigInteger[][] board, List<int[]> priority) {
        int size = board.length;
        int boxSize = (int) Math.sqrt(size);
        for (int h = 0; h < priority.size(); h++) {
            int row = priority.get(h)[0];
            int col = priority.get(h)[1];

            // These variables are just here to limit redundant calculations/array lookups
            BigInteger x = board[row][col];
            int u = (col/boxSize + (row/boxSize)*boxSize);

            for (int k = 0; k < size; k++) {
                    x = x.and(units[0][row][k]);
                    x = x.and(units[1][col][k]);
                    x = x.and(units[2][u][k]);
            }
            board[row][col] = x;
            if (x.bitCount() == 1) {
                units[0][row][col] = x.not();
                units[1][col][row] = x.not();
                units[2][u][((row - (row/boxSize * boxSize))*boxSize + (col - (col/boxSize * boxSize)))] = x.not();
                priority.remove(h);
            }
        }
    }


// Sudoku strategy. Look at the rows, columns and boxes individually and then determine if there is a number that can only be placed in one spot.
    private static void hiddenSingles (BigInteger[][][] units, BigInteger[][] board, List<int[]> priority) {
        int size = units[0].length;
        int boxSize = (int) Math.sqrt(size);
        int counter = 0, row = 0, col = 0;
        int unitRow = 0, unitCol = 0, num = 1;
        int index = 0;
        
        while (index < 3 && (unitRow < size || num <= size || unitCol < size)) {
            BigInteger testVal = valToBinary(num, false);
            
            if (unitCol == size) {
                // System.out.println(index);
                if (index == 0 && counter == 1) {
                    
                    units[0][row][col] = testVal.not();
                    units[1][col][row] = testVal.not();
                    units[2][(col/boxSize + (row/boxSize)*boxSize)][((row - (row/boxSize * boxSize))*boxSize + (col - (col/boxSize * boxSize)))] = testVal.not();
                    board[row][col] = testVal;
                    System.out.println("row = " + row + ", col = " + col);
                    priority.remove(findIndex(priority, new int[] {row,col}));

                } else if (index == 1 && counter == 1) {
                    units[0][col][row] = testVal.not();
                    units[1][row][col] = testVal.not();
                    units[2][(row/boxSize + (col/boxSize)*boxSize)][((col - (col/boxSize * boxSize))*boxSize + (row - (row/boxSize * boxSize)))] = testVal.not();
                    board[col][row] = testVal;

                    priority.remove(findIndex(priority, new int[] {col,row}));

                } else if (index == 2 && counter == 1) {
                    units[0][(row/boxSize*boxSize+col/boxSize)][((row*boxSize-(row/boxSize*(size)))+(col-(col/boxSize*boxSize)))] = testVal.not();
                    units[1][((row*boxSize-(row/boxSize*(size)))+(col-(col/boxSize*boxSize)))][(row/boxSize*boxSize+col/boxSize)] = testVal.not();
                    units[2][row][col] = testVal.not();
                    board[(row/boxSize*boxSize+col/boxSize)][((row*boxSize-(row/boxSize*(size)))+(col-(col/boxSize*boxSize)))] = testVal;

                    priority.remove(findIndex(priority, new int[] {(row/boxSize*boxSize+col/boxSize),((row*boxSize-(row/boxSize*(size)))+(col-(col/boxSize*boxSize)))}));

                }
                num++;
                unitCol = 0;
                counter = 0;
                continue;
            } else if (num > size) {
                unitRow++;
                num = 1;
                continue;
            } else if (unitRow == size) {
                index++;
                unitRow = 0;
            } else if ( (index == 0 && board[unitRow][unitCol].bitCount() > 1 && board[unitRow][unitCol].and(testVal).compareTo(BigInteger.ZERO) != 0) || 
                        (index == 1 && board[unitCol][unitRow].bitCount() > 1 && board[unitCol][unitRow].and(testVal).compareTo(BigInteger.ZERO) != 0) ||
                        (index == 2 && board[(unitRow/boxSize*boxSize+unitCol/boxSize)][((unitRow*boxSize-(unitRow/boxSize*(size)))+(unitCol-(unitCol/boxSize*boxSize)))].bitCount() > 1 && board[(unitRow/boxSize*boxSize+unitCol/boxSize)][((unitRow*boxSize-(unitRow/boxSize*(size)))+(unitCol-(unitCol/boxSize*boxSize)))].and(testVal).compareTo(BigInteger.ZERO) != 0)) {
                counter++;
                row = unitRow;
                col = unitCol;
            }
            unitCol++;
        }

    }

    // NEEDED FOR INDEXING IN HIDDENSINGLES
    private static int findIndex (List<int[]> list, int[] value) {
        for (int i = 0; i < list.size(); i++) {
            if (customEquals(list.get(i), value)) {
                return i;
            }
        }
        return -1;
    }
    // NEEDED FOR INDEXING IN HIDDENSINGLES
    private static boolean customEquals (int[] listElement, int[] value) {
        if (listElement.length > 2 || value.length > 2) {
            System.out.println("ERROR - ARRAY TO BIG");
            return false;
        }
        if (listElement[0] == value[0] && listElement[1] == value[1]) {
            return true;
        } else return false;
    }

// Takes a finsihed sudoku and checks that all the elements in each unit-row is unique, i.e. checks that the sudoku is valid
    private static boolean verification (BigInteger[][][] units) {
        int size = units[0].length;
        BigInteger match = valToBinary(size, true);
        BigInteger matchRow, matchCol, matchBox;
        for (int i = 0; i < size; i++) {
            matchRow = match;
            matchCol = match;
            matchBox = match;
            for (int j = 0; j < size; j++) {
                matchRow = matchRow.and(units[0][i][j]);
                matchCol = matchCol.and(units[1][i][j]);
                matchBox = matchBox.and(units[2][i][j]);
            }
            if (!(matchRow.equals(BigInteger.ZERO) && matchCol.equals(BigInteger.ZERO) && matchBox.equals(BigInteger.ZERO))) {
                return false;
            } 
        }
        return true;
    }
}
