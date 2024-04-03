package dk.dtu.controller;

import java.util.Arrays;

public class PuzzleGenerator {
    private static final int boxsize = 3;
    private static final int cellsRemoved = 35;
    public static int[][] originalBoard;
    public static int[][] cloneBoard;

    static int counter = 0;

    // generates a valid sudoku board
    public static int[][] GenerateSudoku() {
        counter = 0;
        originalBoard = Permutations.shuffle(ValidBoardGen.validBoardGen(boxsize));

        // uncomment to see the board in the console
        // printBoard(originalBoard);
        cloneBoard = deepCopy(originalBoard);
        return removeCells(cloneBoard);
    }

    // removes cells from the board and generates the puzzle
    public static int[][] removeCells(int[][] board) {
        counter++;

        // generate a random number from 0 to 9
        int row = (int) (Math.random() * 9);
        int col = (int) (Math.random() * 9);

        if (counter <= cellsRemoved) {
            if (!(board[row][col] == 0)) {
                int temp = board[row][col];

                // copy contents of board into a tempboard
                int[][] tempBoard = deepCopy(board);

                if (DFSSolver.solveSudoku(tempBoard) != null) {
                    board[row][col] = 0;
                    // System.out.println("could solve!");
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
    public static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    // prints the sudoku board to the console
    public static void printBoard(String[][] board) {
        System.out.println("----------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
