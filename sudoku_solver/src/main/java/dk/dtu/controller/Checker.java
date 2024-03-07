package dk.dtu.controller;

import java.util.Arrays;

public class Checker {

    public static void boardCompleted(String[][] solvedBoard) {
        if (checkSolution(PuzzleGenerator.originalBoard, solvedBoard)) {
            System.out.println("Board is completed");
        } else {
            System.out.println("Board is not completed");
        }
    }

    public static Boolean checkSolution(String[][] originalBoard, String[][] solvedBoard) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!originalBoard[row][col].equals(solvedBoard[row][col])) {
                    return false;
                }
            }
        }
        System.out.println("Board is solved correctly");
        return true;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------//
    // This code is mostly copied from the DFS solver, but since the DFS solver is
    // about to be removed the functions are copied directly.
    // ------------------------------------------------------------------------------------------------------------------------------------//

    // Checks whether a given number in a cell is in order with the rules
    private static boolean isSafe(String[][] board, int row, int col, String num) {
        return !usedInRow(board, row, num) &&
                !usedInCol(board, col, num) &&
                !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    // Checks if the given number is used in the 3x3 box
    private static boolean usedInBox(String[][] board, int startRow, int startCol, String num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + startRow][col + startCol].equals(num)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if the given number is used in the row
    private static boolean usedInRow(String[][] board, int row, String num) {
        for (int col = 0; col < board.length; col++) {
            if (board[row][col].equals(num)) {
                return true;
            }
        }
        return false;
    }

    // Checks if the given number is used in the column
    private static boolean usedInCol(String[][] board, int col, String num) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][col].equals(num)) {
                return true;
            }
        }
        return false;
    }

    private static int[] findEmptyCells(String[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].equals("")) {
                    return new int[] { row, col };
                }
            }
        }
        return null;
    }
}
