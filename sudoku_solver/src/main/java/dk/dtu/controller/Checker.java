package dk.dtu.controller;

public class Checker {

    public static Boolean boardCompleted(int[][] solvedBoard) {
        if (checkSolution(solvedBoard)) {
            System.out.println("Board is completed");
            return true;
        } else {
            return false;
        }
    }

    public static Boolean checkSolution(int[][] solvedBoard) {
        // Check if each row, column, and box contains all of the digits from 1 to 9
        for (int i = 1; i <= 9; i++) {
            for (int row = 0; row < 9; row++) {
                if (!usedInRow(solvedBoard, row, i)) {
                    return false;
                }
            }
            for (int col = 0; col < 9; col++) {
                if (!usedInCol(solvedBoard, col, i)) {
                    return false;
                }
            }
            for (int box = 0; box < 9; box++) {
                if (!usedInBox(solvedBoard, box / 3 * 3, box % 3 * 3, i)) {
                    return false;
                }
            }
        }

        System.out.println("Board is solved correctly");
        return true;
    }

    public static Boolean mistakeMade(int row, int col, int[][] board) {
        for (int i = 0; i < 9; i++) {
            if (i != col && board[row][i] == (board[row][col])) {
                return true;
            }
            if (i != row && board[i][col] == (board[row][col])) {
                return true;
            }
        }
        int boxRow = row / 3 * 3;
        int boxCol = col / 3 * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((boxRow + i != row || boxCol + j != col)
                        && board[boxRow + i][boxCol + j] == (board[row][col])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    public static Boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    public static Boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}