package dk.dtu.controller;

import java.util.Arrays;



public class PuzzleGenerator {
    private static final int boxsize = 3;
    public static String[][] originalBoard;
    public static String[][] cloneBoard;

    static int counter = 0;

    // generates a valid sudoku board
    public static String[][] GenerateSudoku() {
        counter = 0;
        originalBoard = Permutations.shuffle(ValidBoardGen.generateBoard(boxsize));
        cloneBoard = deepCopy(originalBoard);
        return removeCells(cloneBoard);
    }

    // removes cells from the board and generates the puzzle
    public static String[][] removeCells(String[][] board) {
        counter++;

        // generate a random number from 0 to 9
        int row = (int) (Math.random() * 9);
        int col = (int) (Math.random() * 9);

        if (counter <= 60) {
            if (!board[row][col].equals("0")) {
                String temp = board[row][col];
                board[row][col] = "0";
                printBoard(board);

                // copy contents of board into a tempboard
                String[][] tempBoard = deepCopy(board);

                if (DFSSolver.solveSudoku(tempBoard) != null) {
                    board[row][col] = "0";
                    System.out.println("could solve!");
                    removeCells(board);
                } else {
                    board[row][col] = temp;
                    removeCells(board);
                    System.out.println("could not solve!");
                }
            } else {
                removeCells(board);
            }
        }
        return board;

    }

    // makes an exact copy of the original board
    public static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }
    
        final String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }


    // prints the sudoku borad to the console 
    public static void printBoard(String[][] board) {
        // print the board
        System.out.println("----------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
