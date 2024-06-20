package dk.dtu.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PuzzleGeneratorTest {

    @Test
    public void testGenerateSudokuClassic() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Classic");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 32 && emptyCells <= 45);
    }

    @Test
    public void testGenerateSudokuEasy() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Easy");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 16 && emptyCells <= 27);
    }

    @Test
    public void testGenerateSudokuMedium() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Medium");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 32 && emptyCells <= 45);
    }

    @Test
    public void testGenerateSudokuHard() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("Hard");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertTrue(emptyCells >= 48 && emptyCells <= 63);
    }

    @Test
    public void testGenerateSudokuLevel1() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("level1");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertEquals(20, emptyCells);
    }

    @Test
    public void testGenerateSudokuLevel11() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("level11");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertEquals(35, emptyCells);
    }

    @Test
    public void testGenerateSudokuLeve21() {
        int[][] sudoku = PuzzleGenerator.generateSudoku("level21");
        assertNotNull(sudoku);
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCells(sudoku);
        assertEquals(45, emptyCells);
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
        assertEquals(9, sudoku.length);

        int emptyCells = countEmptyCellsBig(sudoku,9);
        assertTrue(emptyCells >= 18 && emptyCells <= 41);
    }

    @Test
    public void testGenerateBigSudoku4x4() {
        int[][] sudoku = PuzzleGenerator.generateBigSudoku(4, false);
        assertNotNull(sudoku);
        assertEquals(16, sudoku.length);

        int emptyCells = countEmptyCellsBig(sudoku,16);
        assertTrue(emptyCells >= 64 && emptyCells <= 128);
    }

    @Test
    public void testGenerateBigSudoku5x5() {
        int[][] sudoku = PuzzleGenerator.generateBigSudoku(5, false);
        assertNotNull(sudoku);
        assertEquals(25, sudoku.length);

        int emptyCells = countEmptyCellsBig(sudoku,25);
        assertTrue(emptyCells >= 125 && emptyCells <= 313);
    }

    @Test
    public void testGenerateSamuraiSudokuUniversal() {
        int[][][] sudoku = PuzzleGenerator.generateSamuraiSudoku();
        assertNotNull(sudoku);
        assertEquals(5, sudoku.length);
        for (int[][] board : sudoku) {
            assertNotNull(board);
            assertEquals(9, board.length);
            int emptyCells = countEmptyCells(board);
            assertTrue(emptyCells >= 36 && emptyCells <= 45);
        }
    }

    @Test
    public void testGenerateSamuraiSudokuIndividual() {
        int[][][] sudoku = PuzzleGenerator.generateSamuraiSudoku();
        int[][] centerBoard = sudoku[0];
        int[][] topLeftBoard = sudoku[1];
        int[][] topRightBoard = sudoku[2];
        int[][] bottomLeftBoard = sudoku[3];
        int[][] bottomRightBoard = sudoku[4];

        // test center board
        assertNotNull(centerBoard);
        assertEquals(9, centerBoard.length);
        int emptyCells = countEmptyCells(centerBoard);
        assertTrue(emptyCells >= 36 && emptyCells <= 45);

        // test top left board
        assertNotNull(topLeftBoard);
        assertEquals(9, topLeftBoard.length);
        emptyCells = countEmptyCells(topLeftBoard);
        assertTrue(emptyCells >= 36 && emptyCells <= 45);

        // test top right board
        assertNotNull(topRightBoard);
        assertEquals(9, topRightBoard.length);
        emptyCells = countEmptyCells(topRightBoard);
        assertTrue(emptyCells >= 36 && emptyCells <= 45);

        // test bottom left board
        assertNotNull(bottomLeftBoard);
        assertEquals(9, bottomLeftBoard.length);
        emptyCells = countEmptyCells(bottomLeftBoard);
        assertTrue(emptyCells >= 36 && emptyCells <= 45);

        // test bottom right board
        assertNotNull(bottomRightBoard);
        assertEquals(9, bottomRightBoard.length);
        emptyCells = countEmptyCells(bottomRightBoard);
        assertTrue(emptyCells >= 36 && emptyCells <= 45);
    }
}
