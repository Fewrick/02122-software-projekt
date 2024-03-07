package dk.dtu.controller;

import dk.dtu.view.medium.SudokuBoard;
import javafx.event.ActionEvent;

public class DFSSolver {

      // Sudoku solver that utilizes backtracking and a DFS search to solve a sudoku
      public static String[][] solveSudoku(String[][] board) {
        int N = board.length;

        // creates a list of all the empty cells
        int[] emptyCells = findEmptyCells(board);

        // If there are no empty cells left, the sudoku is completed
        if (emptyCells == null) {
            return board;
        }

        int row = emptyCells[0];
        int col = emptyCells[1];

        // recursively check for each number in each cell
        for (int num = 1; num <= N; num++) {
            if (isSafe(board, row, col, String.valueOf(num))) {
                board[row][col] = String.valueOf(num);

                String[][] solvedBoard = solveSudoku(board);
                if (solvedBoard != null) {
                    return solvedBoard;
                }

                board[row][col] = ""; // The backtracking step, this ensures that the cell is put back into the list
            }
        }

        return null; // Return null if no solution is found
    }


    // Checks whether a given number is safe to place in a given cell on the board
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

    public static void solveSudoku(ActionEvent event) throws Exception {
        Boolean solvable = (solveSudoku(BasicBoard.puzzleBoard) != null);
        if (solvable) {
            BasicBoard.createSudoku(SudokuBoard.pane);
            System.out.println("Solved!");
        } else {
            System.out.println("Could not compute");
        }
    }
}
