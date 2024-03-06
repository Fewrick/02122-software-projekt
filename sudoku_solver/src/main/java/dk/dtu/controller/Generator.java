package dk.dtu.controller;

import java.util.Arrays;

import dk.dtu.view.medium.Board;
import javafx.event.ActionEvent;

public class Generator {
    private static final int boxsize = 3;
    private static String[][] board = Board.gridComplete;
    static int counter = 0;

    public static String[][] GenerateSudoku() {
        counter = 0;
        return removeCells(Permutations.shuffle(ValidBoardGen.validBoardGen(boxsize)));
    }

    public static void GenerateSudoku(ActionEvent arg0) {

        printBoard(removeCells(Permutations.shuffle(board)));
    }

    public static String[][] removeCells(String[][] board) {
        counter++;

        // generate a random number from 0 to 9
        int row = (int) (Math.random() * 9);
        int col = (int) (Math.random() * 9);

        if (counter <= 60) {
            if (!board[row][col].equals("0")) {
                String temp = board[row][col];
                board[row][col] = "0";
                printBoard(board);

                // copy contents of board into a tempboard
                String[][] tempBoard = deepCopy(board);

                if (DFSSolver.solveSudoku(tempBoard) != null) {
                    board[row][col] = "0";
                    System.out.println("could solve!");
                    removeCells(board);
                } else {
                    board[row][col] = temp;
                    removeCells(board);
                    System.out.println("could not solve!");
                }
            } else {
                removeCells(board);
            }
        }
        return board;

    }

    
    public static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }
    
        final String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }



    public static void printBoard(String[][] board) {
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
