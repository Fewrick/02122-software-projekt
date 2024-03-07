package dk.dtu.controller;

public class Checker {

    public static Boolean boardCompleted(String[][] solvedBoard) {
        if (checkSolution(PuzzleGenerator.originalBoard, solvedBoard)) {
            System.out.println("Board is completed");
            return true;
        } else {
            System.out.println("Board is not completed");
            return false;
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
}