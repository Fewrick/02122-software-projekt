package dk.dtu.controller;

public class Checker {

    public static Boolean boardCompleted(String[][] solvedBoard) {
        if (checkSolution(solvedBoard)) {
            System.out.println("Board is completed");
            return true;
        } else {
            System.out.println("Board is not completed");
            return false;
        }
    }

    public static Boolean checkSolution(String[][] solvedBoard) {
        // Check if each row, column, and box contains all of the digits from 1 to 9
        for (int i = 1; i <= 9; i++) {
            for (int row = 0; row < 9; row++) {
                if (!usedInRow(solvedBoard, row, Integer.toString(i))) {
                    return false;
                }
            }
            for (int col = 0; col < 9; col++) {
                if (!usedInCol(solvedBoard, col, Integer.toString(i))) {
                    return false;
                }
            }
            for (int box = 0; box < 9; box++) {
                if (!usedInBox(solvedBoard, box / 3 * 3, box % 3 * 3, Integer.toString(i))) {
                    return false;
                }
            }
        }

        System.out.println("Board is solved correctly");
        return true;
    }

    public static Boolean usedInRow(String[][] board, int row, String num) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col].equals(num)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean usedInCol(String[][] board, int col, String num) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col].equals(num)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean usedInBox(String[][] board, int boxStartRow, int boxStartCol, String num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + boxStartRow][col + boxStartCol].equals(num)) {
                    return true;
                }
            }
        }
        return false;
    }
}