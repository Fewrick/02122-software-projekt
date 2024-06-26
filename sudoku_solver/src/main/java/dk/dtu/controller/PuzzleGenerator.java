package dk.dtu.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator {
    public static int[][] originalBoard;
    public static int[][][] boards;

    static int cellsRemoved = 0;
    static int minCellsRemoved;
    static int maxCellsRemoved;

    /**
     * Generates a valid 3x3 sudoku board with the specified difficulty.
     *
     * @param difficulty the difficulty level of the sudoku board. Valid values are
     *                   "Classic", "Easy",
     *                   "Medium", "Hard", or a string starting with "level"
     *                   followed by a number.
     *                   For example, "level1", "level2", etc.
     * @return a 2D integer array representing the generated sudoku board.
     *         for different sizes see "generateBigSudoku"
     */
    public static int[][] generateSudoku(String difficulty) {
        System.out.println("Generating sudoku...");
        long startTime = System.currentTimeMillis();
        int boxSize = 3;
        int size = 9;
        int[][] cloneBoard = new int[size][size];

        if (difficulty.equals("Classic")) {
            minCellsRemoved = (int) (Math.pow(size, 2) * 0.4);
            maxCellsRemoved = (int) (Math.pow(size, 2) * 0.5);
        } else if (difficulty.equals("Easy")) {
            minCellsRemoved = (int) (Math.pow(size, 2) * 0.2);
            maxCellsRemoved = (int) (Math.pow(size, 2) * 0.3);
        } else if (difficulty.equals("Medium")) {
            minCellsRemoved = (int) (Math.pow(size, 2) * 0.4);
            maxCellsRemoved = (int) (Math.pow(size, 2) * 0.5);
        } else if (difficulty.equals("Hard")) {
            minCellsRemoved = (int) (Math.pow(size, 2) * 0.6);
            maxCellsRemoved = (int) (Math.pow(size, 2) * 0.7);
        } else if (difficulty.startsWith("level")) {
            int level = Integer.parseInt(difficulty.substring(5));
            if (level <= 10) {
                minCellsRemoved = maxCellsRemoved = 20 + 3 * ((level - 1) / 2);
            } else if (level <= 20) {
                minCellsRemoved = maxCellsRemoved = 35 + 2 * ((level - 11) / 2);
            } else {
                minCellsRemoved = maxCellsRemoved = 45 + ((level - 21) / 2);
            }
        }

        cellsRemoved = 0;
        originalBoard = Permutations.shuffle(ValidBoardGen.generateBoard(boxSize));

        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = 0;
            cloneBoard = deepCopy(originalBoard);
            cloneBoard = removeCells(cloneBoard, false);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Generated sudoku in " + elapsedTime + " ms");
        return cloneBoard;

    }

    // Generates a sudoku of size "boxSize" x "boxSize", and removes 50% of the
    // numbers. For boxSize > 5 uniqueness is not guaranteed
    // unless "unique" is set to true, in which case 20-50% of the numbers will be
    // removed. Doing this will result in a very slow generation due to the amount
    // of checks.
    public static int[][] generateBigSudoku(int boxSize, boolean unique) {
        System.out.println("Generating big sudoku...");
        long startTime = System.currentTimeMillis();
        int size = boxSize * boxSize;
        int[][] cloneBoard = new int[size][size];
        minCellsRemoved = (int) (Math.pow(size, 2) * 0.2);
        maxCellsRemoved = (int) (Math.pow(size, 2) * 0.5);

        cellsRemoved = 0;
        originalBoard = Permutations.shuffle(ValidBoardGen.generateBoard(boxSize));

        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = 0;
            cloneBoard = deepCopy(originalBoard);
            cloneBoard = removeCells(cloneBoard, unique);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Generated big sudoku in " + elapsedTime + " ms");
        return cloneBoard;
    }

    // Generates 5 valid 3x3 sudoku boards, where the overlapping corners have the
    // same elements removed
    // To get a board call "arrayname"[boardnumber] - only the first [] is needed in
    // the call.
    // [0] = Center, [1] = Top Left, [2] = Top Right, [3] = Bottom Left, [4] =
    // Bottom Right
    public static int[][][] generateSamuraiSudoku() {
        int boxSize = 3;
        int size = 9;
        boards = Permutations.shuffleSamurai(ValidBoardGen.generateBoard(boxSize));
        int[][] boardCenter = deepCopy(boards[0]);
        int[][] boardTopLeft = deepCopy(boards[1]);
        int[][] boardTopRight = deepCopy(boards[2]);
        int[][] boardBottomLeft = deepCopy(boards[3]);
        int[][] boardBottomRight = deepCopy(boards[4]);

        cellsRemoved = 0;
        minCellsRemoved = (int) (Math.pow(size, 2) * 0.4);
        maxCellsRemoved = (int) (Math.pow(size, 2) * 0.5);

        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = 0;
            boardCenter = deepCopy(boards[0]);
            boardCenter = removeCells(boardCenter, false);
        }

        for (int i = 0; i < size; i++) {
            boardTopLeft[i / boxSize + size - boxSize][i - i / boxSize * boxSize + size
                    - boxSize] = boardCenter[i / boxSize][i - i / boxSize * boxSize];
            boardTopRight[i / boxSize + size - boxSize][i - i / boxSize * boxSize] = boardCenter[i / boxSize][i
                    - i / boxSize * boxSize + size - boxSize];
            boardBottomLeft[i / boxSize][i - i / boxSize * boxSize + size
                    - boxSize] = boardCenter[i / boxSize + size - boxSize][i - i / boxSize * boxSize];
            boardBottomRight[i / boxSize][i - i / boxSize * boxSize] = boardCenter[i / boxSize + size - boxSize][i
                    - i / boxSize * boxSize + size - boxSize];
        }
        int[][] boardTopLeftAlligned = deepCopy(boardTopLeft);
        int[][] boardTopRightAlligned = deepCopy(boardTopRight);
        int[][] boardBottomLeftAlligned = deepCopy(boardBottomLeft);
        int[][] boardBottomRightAlligned = deepCopy(boardBottomRight);

        cellsRemoved = 0;
        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = zeroCount(boardTopLeftAlligned);
            boardTopLeft = deepCopy(boardTopLeftAlligned);
            boardTopLeft = removeCells(boardTopLeft, false);
        }
        cellsRemoved = 0;
        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = zeroCount(boardTopRightAlligned);
            boardTopRight = deepCopy(boardTopRightAlligned);
            boardTopRight = removeCells(boardTopRight, false);
        }
        cellsRemoved = 0;
        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = zeroCount(boardBottomLeftAlligned);
            boardBottomLeft = deepCopy(boardBottomLeftAlligned);
            boardBottomLeft = removeCells(boardBottomLeft, false);
        }
        cellsRemoved = 0;
        while (cellsRemoved < minCellsRemoved && minCellsRemoved <= maxCellsRemoved) {
            cellsRemoved = zeroCount(boardBottomRightAlligned);
            boardBottomRight = deepCopy(boardBottomRightAlligned);
            boardBottomRight = removeCells(boardBottomRight, false);
        }
        return new int[][][] { boardCenter, boardTopLeft, boardTopRight, boardBottomLeft, boardBottomRight };
    }

    // Counts the number of 0's on a board, i.e. number of empty cells
    public static int zeroCount(int[][] board) {
        int size = board.length;
        int counter = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0)
                    counter++;
            }
        }
        return counter;
    }

    // Removes cells from the board and generates the puzzle
    private static int[][] removeCells(int[][] board, boolean unique) {
        int size = board.length;
        int boxSize = (int) Math.sqrt(size);
        Random rand = new Random();
        List<int[]> cells = new ArrayList<int[]>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int[] t = { row, col };
                cells.add(t);
            }
        }

        while (cells.size() > 0 && cellsRemoved < maxCellsRemoved) {
            int cell = rand.nextInt(cells.size());
            int row = cells.get(cell)[0];
            int col = cells.get(cell)[1];
            cells.remove(cell);

            int cellValue = board[row][col];
            board[row][col] = 0;

            if ((boxSize > 5 && !unique) || LogicSolver.validCheck(board)) {
                cellsRemoved++;
            } else
                board[row][col] = cellValue;

        }
        return board;
    }

    // Makes a copy of the original board
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
}
