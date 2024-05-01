package dk.dtu.controller;

import java.util.Arrays;

public class PuzzleGenerator {
    private static int boxsize = 3;
    public static int[][] originalBoard;
    public static int[][] cloneBoard;

    static int runThroughs = 0;
    static int cellsRemoved = 0;
    static int maxRunThroughs;
    static int maxCellsRemoved;

    // generates a valid sudoku board
    public static int[][] GenerateSudoku(String difficulty, int boardSize) {
        boxsize = (int) Math.sqrt(boardSize);
        if (difficulty.equals("Classic")) {
            maxRunThroughs = 3;
            maxCellsRemoved = 1;
        } else if (difficulty.equals("Medium")) {
            maxRunThroughs = 3;
            maxCellsRemoved = 40;
        } else if (difficulty.equals("Hard")) {
            maxRunThroughs = 1000;
            maxCellsRemoved = 63;
        } else if (difficulty.equals("Custom")) {
            // TODO: Implement custom difficulty
            maxRunThroughs = 1000;
            maxCellsRemoved = 63;
        }

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

        // generate a random number from 0 to boardsize
        int row = (int) (Math.random() * Math.pow(boxsize, 2));
        int col = (int) (Math.random() * Math.pow(boxsize, 2));

        if (!(board[row][col] == 0)) {
            int temp = board[row][col];
            board[row][col] = 0;

            cellsRemoved++;

            if (LogicSolver.validCheck(board) && !(cellsRemoved > maxCellsRemoved)) {
                // System.out.println("could solve!");
                removeCells(board);
            } else if ((!LogicSolver.validCheck(board))
                    && (runThroughs > maxRunThroughs || cellsRemoved > maxCellsRemoved)) {
                cellsRemoved--;
                System.out.println("Ran through " + maxRunThroughs + " times, cells removed: " + cellsRemoved + "/"
                        + maxCellsRemoved);
                board[row][col] = temp;
                return board;
            } else if (cellsRemoved > maxCellsRemoved) {
                cellsRemoved--;
                board[row][col] = temp;
                System.out.println("Ran through " + maxRunThroughs + " times, cells removed: " + cellsRemoved + "/"
                        + maxCellsRemoved);
                return board;
                // System.out.println("could not solve! -Retrying with different cell");
            } else {
                runThroughs++;
                cellsRemoved--;
                board[row][col] = temp;
                removeCells(board);
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
