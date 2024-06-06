package dk.dtu.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidBoardGenTest {

    @Test
    public void testGenerateBoard() {
        // Test the generateBoard method with boxsize = 2
        int[][] generatedBoard = ValidBoardGen.generateBoard(2);
        assertTrue(isValidSudoku(generatedBoard));
    
        // Test the generateBoard method with boxsize = 3
        generatedBoard = ValidBoardGen.generateBoard(3);
        assertTrue(isValidSudoku(generatedBoard));
    
        // Test the generateBoard method with boxsize = 4
        generatedBoard = ValidBoardGen.generateBoard(4);
        assertTrue(isValidSudoku(generatedBoard));
    }

    // Helper method to check if a board is a valid sudoku
    private boolean isValidSudoku(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int currentValue = board[i][j];
                for (int k = 0; k < board.length; k++) {
                    if (k != i && board[k][j] == currentValue) {
                        return false;
                    }
                }
                for (int k = 0; k < board[i].length; k++) {
                    if (k != j && board[i][k] == currentValue) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}