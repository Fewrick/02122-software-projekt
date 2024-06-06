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
        int boxSize = (int) Math.sqrt(size);
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
        
        BigInteger x;
        int u;
        int cnt = 0;
        int prioritySizePrev = priority.size();
        int prioritySizeNow = priority.size();
        while (priority.size() > 0) {
            prioritySizePrev = priority.size();
            for (int h = 0; h < priority.size(); h++) {
                int row = priority.get(h)[0];
                int col = priority.get(h)[1];

                // These variables are just here to limit redundant calculations/array lookups
                x = board[row][col];
                u = (col/boxSize + (row/boxSize)*boxSize);

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
            prioritySizeNow = priority.size();
            if (prioritySizeNow == prioritySizePrev) {
                if (cnt == 1) break;
                cnt++;
            } else cnt = 0;
        }
        return units;
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
