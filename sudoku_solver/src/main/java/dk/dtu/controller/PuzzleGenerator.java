package dk.dtu.controller;

import java.util.Arrays;

public class PuzzleGenerator {
    private static final int boxsize = 3;
    public static int[][] originalBoard;
    public static int[][] cloneBoard;

    static int runThroughs = 0;
    static int cellsRemoved = 0;
    static int maxRunThroughs = 10;

    // generates a valid sudoku board
    public static int[][] GenerateSudoku() {
        cellsRemoved = 0;
        runThroughs = 0;
        originalBoard = Permutations.shuffle(ValidBoardGen.generateBoard(boxsize));

        // uncomment to see the board in the console
        // printBoard(originalBoard);
        cloneBoard = deepCopy(originalBoard);
        return removeCells(cloneBoard);
    }

    // removes cells from the board and generates the puzzle
    // removes around 45-56 cells with at most 10 runthroughs
    public static int[][] removeCells(int[][] board) {

        // generate a random number from 0 to 9
        int row = (int) (Math.random() * 9);
        int col = (int) (Math.random() * 9);

            if (!(board[row][col] == 0)) {
                int temp = board[row][col];
                board[row][col] = 0;

                cellsRemoved++;

                // copy contents of board into a tempboard
                int[][] tempBoard = deepCopy(board);

                if (LogicSolver.validCheck(tempBoard)) {
                    // System.out.println("could solve!");
                    removeCells(board);
                } else if (!(LogicSolver.validCheck(tempBoard)) && runThroughs >= maxRunThroughs) {
                    System.out.println("Ran through " + maxRunThroughs + " times, cells removed: " + cellsRemoved);
                    return board;
                } else {
                    runThroughs++;
                    cellsRemoved--;
                    board[row][col] = temp;
                    removeCells(board);
                    // System.out.println("could not solve! -Retrying with different cell");
                }
            } else {
                removeCells(board);
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
