package dk.dtu.controller;

public class Solver {

    // Sudoku solver that utilizes backtracking and a DFS search to solve a sudoku
    public static boolean solveSudoku(String[][] board) {
        int N = board.length;

        // creates a list of all the empty cells
        int[] emptyCells = findEmptyCells(board);

        // If there are no empty cells left, the sudoku is completed
        if (emptyCells == null) {
            return true;
        }

        int row = emptyCells[0];
        int col = emptyCells[1];

        // recursively check for each number in each cell
        for (int num = 1; num <= N; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = "" + num;

                if (solveSudoku(board)) {
                    return true;
                }

                board[row][col] = "0"; // The backtracking step, this ensures that the cell is put back into the list
                                       // and the method continues with the next number
            }
        }

        return false;
    }

    // Checks wether a given number is safe to place in a given cell on the board
    private static boolean isSafe(String[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) &&
                !usedInCol(board, col, num) &&
                !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    // Checks if the given number is used in the 3x3 box
    private static boolean usedInBox(String[][] board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + startRow][col + startCol] == "" + num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if the given number is used in the row
    private static boolean usedInRow(String[][] board, int row, int num) {
        for (int col = 0; col < board.length; col++) {
            if (board[row][col] == "" + num) {
                return true;
            }
        }
        return false;
    }

    // Checks if the given number is used in the column
    private static boolean usedInCol(String[][] board, int col, int num) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][col] == "" + num) {
                return true;
            }
        }
        return false;
    }

    private static int[] findEmptyCells(String[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == "0") {
                    return new int[] { row, col };
                }
            }
        }
        return null;
    }
}
