package dk.dtu.controller;

public class ValidBoardGen {
// Generates a valid sudoku.
    public static int[][] generateBoard (int boxSize) {
        int size = boxSize*boxSize;
        int [][] board = new int[size][size];
        int x = 0;
        // Fills in the first row of boxes
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < size; j++) {
                if (j + i * boxSize < size) {
                    board[i][j + i * boxSize] = (j + 1);
                    x = (j + 1) / boxSize;
                } else board[i][j - x * boxSize] = (j + 1);
            }
        }
        // Offsets the individual coloumns of the box above so a valid sudoku is created
        for (int i = 1; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                for (int k = 0; k < boxSize; k++) {
                    for (int l = 0; l < boxSize; l++) {
                        if (l + 1 == boxSize) {
                            board[i * boxSize + k][j * boxSize + l] = board[(i - 1) * boxSize + k][j * boxSize];
                        } else board[i * boxSize + k][j * boxSize + l] = board[(i - 1) * boxSize + k][j * boxSize + l + 1];;
                    }
                }
            }
        }

        return board;
    }

}
