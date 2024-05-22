package dk.dtu.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PuzzleGeneratorTest {

    @Test
    public void testGenerateSudokuClassic() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Classic");
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 9);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 32 && emptyCells <= 45);
    }

    @Test
    public void testGenerateSudokuEasy() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Easy");
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 9);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 16 && emptyCells <= 27);
    }

    @Test
    public void testGenerateSudokuMedium() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Medium");
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 9);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 32 && emptyCells <= 45);
    }

    @Test
    public void testGenerateSudokuHard() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Hard");
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 9);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 48 && emptyCells <= 63);
    }

    private int countEmptyCells(int[][] sudoku) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }
}