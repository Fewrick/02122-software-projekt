package dk.dtu.controller;

import java.util.ArrayList;
import java.util.List;

public class LogicSolver {

// takes a normal board and solves and checks it. Returns true if it can be solved deterministically, i.e. no guesses are required to solve the sudoku
    public static boolean validCheck (int[][] board) {
        return verification(solver(mirrorBoard(board)));
    }

// Generates a unit (long[][]) for each rule of the sudoku, 0 = rows, 1 = columns, 2 = boxes.
    private static long[][][] boardUnits (long[][] board){
        int size = board.length;
        int boxSize = (int) Math.sqrt(board.length);
        long[][] unitsRow = new long[size][size];  // 3 layers, one for rows=0, one for columns=1, one for boxes=2
        long[][] unitsCol = new long[size][size];
        long[][] unitsBox = new long[size][size];

        long x;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if (Long.toBinaryString(board[i][j]).contains("11")) {
                    x = board[i][j];
                } else x = ~board[i][j];

                unitsRow[i][j] = x;                                                                 // the row units.
                unitsCol[j][i] = x;                                                                 // the col units.
                unitsBox[(j/boxSize + (i/boxSize)*boxSize)][((i - (i/boxSize * boxSize))*boxSize + (j - (j/boxSize * boxSize)))] = x;        // the box units.
            }
        }      
        return new long[][][] {unitsRow, unitsCol, unitsBox};
    }
    
// Takes an integer values, and represents that value as the index in a binary string, when "oneOrZero" = false (0). i.e. 1=0001, 2=0010, 3=0100, ... - works for up to 63 bits.
// When "oneOrZero" = true (1), a string of 1's the size of the board length is created, this is used for the empty fields on the board.
    private static long valToBinary (long num, boolean oneOrZero) {
        String t = "";
        if (oneOrZero) {
            for (int i = 0; i < num; i++) {
                t = "1" + t;
            }    
        } else {
            for (int i = 1; i < num; i++) {
                t = "0" + t;
            }
            t = "1" + t;
        }
        return Long.parseLong(t,2);
    }

// Takes the original board, and creates a copy of where each cell is defined by a binary value.
    private static long[][] mirrorBoard (int[][] board) {
        int size = board.length;
        long[][] binBoard = new long[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    binBoard[i][j] = valToBinary(size, true);
                } else {
                    binBoard[i][j] = valToBinary(Long.valueOf(board[i][j]), false);
                }
            }
        }
        return binBoard;
    }
    
// can descrcibe each square as a triple (rows, columns, box), row = i, col = j, box = (i/boxSize)*boxSize + j/boxSize)
    private static long[][][] solver (long[][] board) {
        int size = board.length;
        int boxSize = (int) Math.sqrt(size);
        long[][][] units = boardUnits(board);
        List<int[]> priority = new ArrayList<int[]>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                    int[] t = {i,j};
                if (Long.toBinaryString(board[i][j]).contains("11")) {
                    priority.add(t);
                }        
            }
        }
        
        long x;
        int u;
        int cnt = 0;
        int priSizePrev = priority.size();
        int priSizeNow = priority.size();
        while (priority.size() > 0) {
            priSizePrev = priority.size();
            for (int h = 0; h < priority.size(); h++) {
                int i = priority.get(h)[0];
                int j = priority.get(h)[1];

                // These variables are just here limit redundant calculations/array lookups
                x = board[i][j];
                u = (j/boxSize + (i/boxSize)*boxSize);

                for (int k = 0; k < size; k++) {
                        x = x & units[0][i][k];
                        x = x & units[1][j][k];
                        x = x & units[2][u][k];
                }
                board[i][j] = x;
                if (Long.highestOneBit(x) == Long.lowestOneBit(x)) {
                    units[0][i][j] = ~x;
                    units[1][j][i] = ~x;
                    units[2][u][((i - (i/boxSize * boxSize))*boxSize + (j - (j/boxSize * boxSize)))] = ~x;
                    priority.remove(h);
                }
            }
            priSizeNow = priority.size();
            if (priSizeNow == priSizePrev) {
                if (cnt == 1) break;
                cnt++;
            } else cnt = 0;
        }
        return units;
    }

// Takes a finsihed sudoku and checks that all the elements in each unit-row is unique, i.e. checks that the sudoku is valid
    private static boolean verification (long[][][] units) {
        int size = units[0].length;
        long match = valToBinary(size, true);
        long matchRow, matchCol, matchBox;
        for (int i = 0; i < size; i++) {
            matchRow = match;
            matchCol = match;
            matchBox = match;
            for (int j = 0; j < size; j++) {
                matchRow = matchRow & units[0][i][j];
                matchCol = matchCol & units[1][i][j];
                matchBox = matchBox & units[2][i][j];
            }
            if (!(matchRow == 0 && matchCol == 0 && matchBox == 0)) {
                return false;
            } 
        }
        return true;
    }
}
