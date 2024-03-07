package dk.dtu.controller;

public class ValidBoardGen {

    // Generates a valid sudoku board based on how many boxes the user wants e.g. 3x3, 4x4, 5x5
    public static String[][] generateBoard (int boxSize) {
        int size = boxSize*boxSize;
        String [][] board = new String[size][size];
        int x = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < size; j++) {
                if (j + i * boxSize < size) {
                    board[i][j + i * boxSize] = "" + (j + 1);
                    x = (j + 1) / boxSize;
                } else board[i][j - x * boxSize] = "" + (j + 1);
            }
        }

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
