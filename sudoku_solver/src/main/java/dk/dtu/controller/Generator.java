package dk.dtu.controller;

import java.util.Arrays;

import dk.dtu.view.medium.Board;
import javafx.event.ActionEvent;

public class Generator {
    private static int[][] board = Board.gridComplete;
    static int counter = 0;

    public static void GenerateSudoku(ActionEvent arg0) {

        printBoard(removeCells(board));
    }

    public static int[][] removeCells(int[][] board) {
        counter++;

        // generate a random number from 0 to 9
        int row = (int) (Math.random() * 9);
        int col = (int) (Math.random() * 9);

        if (counter <= 20) {
            if (board[row][col] != 0) {
                int temp = board[row][col];
                board[row][col] = 0;
                printBoard(board);

                // copy contents of board into a tempboard
                int[][] tempBoard = deepCopy(board);
                printBoard(tempBoard);

                if (DFSSolver.solveSudoku(tempBoard)) {
                    board[row][col] = 0;
                    System.out.println("could solve!");
                    removeCells(board);
                } else {
                    board[row][col] = temp;
                    removeCells(board);
                }
                // checkSolution(board);

            }
        }
        return board;

    }

    
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

    public static void printBoard(int[][] board) {
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
