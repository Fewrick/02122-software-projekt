package dk.dtu.controller;

public class EasyChecker {

    public static Boolean boardCompleted(int[][] solvedBoard) {
        if (checkSolution(solvedBoard)) {
            System.out.println("Board is completed");
            return true;
        } else {
            return false;
        }
    }

    public static Boolean checkSolution(int[][] solvedBoard) {
        // Check if each row, column, and box contains all of the digits from 1 to 4
        for (int i = 1; i <= 4; i++) {
            for (int row = 0; row < 4; row++) {
                if (!usedInRow(solvedBoard, row, i)) {
                    return false;
                }
            }
            for (int col = 0; col < 4; col++) {
                if (!usedInCol(solvedBoard, col, i)) {
                    return false;
                }
            }
            for (int box = 0; box < 4; box++) {
                if (!usedInBox(solvedBoard, box / 2 * 2, box % 2 * 2, i)) {
                    return false;
                }
            }
        }

        System.out.println("Board is solved correctly");
        return true;
    }

    public static Boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < 4; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    public static Boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < 4; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    public static Boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}