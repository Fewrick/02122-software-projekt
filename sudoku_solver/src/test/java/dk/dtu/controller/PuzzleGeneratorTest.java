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

    @Test
    public void testGenerateSudokulevel1() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Level 1");
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 9);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 20 && emptyCells <= 45);
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
    private int countEmptyCellsBig(int[][] sudoku, int size) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (sudoku[i][j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    @Test
    public void testGenerateBigSudoku3x3() {
        int[][] sudoku = PuzzleGenerator.generateBigSudoku(3, false);
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 9);

        int emptyCells = countEmptyCellsBig(sudoku,9);
        assertTrue(emptyCells >= 18 && emptyCells <= 41);
    }

    @Test
    public void testGenerateBigSudoku4x4() {
        int[][] sudoku = PuzzleGenerator.generateBigSudoku(4, false);
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 16);

        int emptyCells = countEmptyCellsBig(sudoku,16);
        assertTrue(emptyCells >= 64 && emptyCells <= 128);
    }

    @Test
    public void testGenerateBigSudoku5x5() {
        int[][] sudoku = PuzzleGenerator.generateBigSudoku(5, false);
        assertNotNull(sudoku);
        assertTrue(sudoku.length == 25);

        int emptyCells = countEmptyCellsBig(sudoku,25);
        assertTrue(emptyCells >= 125 && emptyCells <= 313);
    }
}
