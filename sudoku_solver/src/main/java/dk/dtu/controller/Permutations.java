package dk.dtu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Permutations {
    
// Generates 5 individual boards where the corners line up for a samurai sudoku. Returns an array of boards with the following index:
// [0] = Center, [1] = Top Left, [2] = Top Right, [3] = Bottom Left, [4] = Bottom Right
    public static int[][][] shuffleSamurai (int[][] board) {
        int size = board.length;
        int boxSize = (int) Math.sqrt(board.length);
        int[][] boardCenter = shuffle(board);
        int[][] boardTopLeft = shuffle(board);
        int[][] boardTopRight = shuffle(board);
        int[][] boardBottomLeft = shuffle(board);
        int[][] boardBottomRight = shuffle(board);
        int[] cTopLeftBox = new int[size];
        int[] cTopRightBox = new int[size];
        int[] cBottomLeftBox = new int[size];
        int[] cBottomRightBox = new int[size];
        int[] tlBottomRightBox = new int[size];
        int[] trBottomLeftBox = new int[size];
        int[] blTopRightBox = new int[size];
        int[] brTopLeftBox = new int[size];

        for (int i = 0; i < size; i++) {
            cTopLeftBox[i] = boardCenter[i / boxSize][i - i / boxSize*boxSize];
            cTopRightBox[i] = boardCenter[i / boxSize][i - i / boxSize*boxSize + size - boxSize];
            cBottomLeftBox[i] = boardCenter[i / boxSize + size - boxSize][i - i / boxSize*boxSize];
            cBottomRightBox[i] = boardCenter[i / boxSize + size - boxSize][i - i / boxSize*boxSize + size - boxSize];
            tlBottomRightBox[i] = boardTopLeft[i / boxSize + size - boxSize][i - i / boxSize*boxSize + size - boxSize];
            trBottomLeftBox[i] = boardTopRight[i / boxSize + size - boxSize][i - i / boxSize*boxSize];
            blTopRightBox[i] = boardBottomLeft[i / boxSize][i - i / boxSize*boxSize + size - boxSize];
            brTopLeftBox[i] = boardBottomRight[i / boxSize][i - i / boxSize*boxSize];
        }

        boardTopLeft = numberExchange(boardTopLeft, tlBottomRightBox, cTopLeftBox);
        boardTopRight = numberExchange(boardTopRight, trBottomLeftBox, cTopRightBox);
        boardBottomLeft = numberExchange(boardBottomLeft, blTopRightBox, cBottomLeftBox);
        boardBottomRight = numberExchange(boardBottomRight, brTopLeftBox, cBottomRightBox);
        
        return new int[][][] {boardCenter, boardTopLeft, boardTopRight, boardBottomLeft, boardBottomRight};
    }

// Standard shuffle algorithm, calls all functions below to generate a new and unique board
   public static int[][] shuffle(int[][] board) {
        return numberExchange(rowSwap(columnSwap(rotate(flipVertical(flipHorizontal(board))))), null, null);
    }

// Replaces all numbers in the sudoku with a different number (i.e. all 1 = 5, all 2 = 3, ...)
    private static int[][] numberExchange(int[][] board, int[] intial, int[] replacement) {
        int size = board.length;
        Random rand = new Random();
        List<Integer> initialNumbers = new ArrayList<Integer>();
        List<Integer> orderRandomizer = new ArrayList<Integer>();
        List<Integer> replacementNumbers = new ArrayList<Integer>();
        if (intial != null && replacement != null) {
            for (int i = 0; i < size; i++){
                initialNumbers.add(intial[i]);
                replacementNumbers.add(replacement[i]);
            }
        } else {
            for (int i = 0; i < size; i++) {
                orderRandomizer.add(i+1);
                initialNumbers.add(i+1);
            }
            for (int i = 0; i < size; i++) {
                int n = rand.nextInt(orderRandomizer.size());
                replacementNumbers.add(orderRandomizer.get(n));
                orderRandomizer.remove(n);
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (initialNumbers.get(k) == board[i][j]) {
                        board[i][j] = replacementNumbers.get(k);
                        break;
                    }
                }
            }
        }
        return board;
    }

// Flips the board around the vertical axis.
    private static int[][] flipVertical (int[][] board) {
        Random rand = new Random();
        int flip = rand.nextInt(2);

        int[][] board2 = new int[board.length][board.length];
        if (flip == 1){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board2[i][board.length-1 - j] = board[i][j];
                }
            }
        } else return board;
        return board2;
    }

// Flips the board around the horizontal axis.
    private static int[][] flipHorizontal (int[][] board) {
        Random rand = new Random();
        int flip = rand.nextInt(2);

        int[][] board2 = new int[board.length][board.length];
        if (flip == 1){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board2[board.length-1 - j][i] = board[j][i];
                }
            }
        } else return board;
        return board2;
    }

// Rotates 0 (0), 90 (1), 180 (2) or 270 (3) degrees
    private static int[][] rotate(int[][] board){
        Random rand = new Random();
        int rot = rand.nextInt(4);

        int[][] board2 = new int[board.length][board.length];
        switch (rot) {
        // Rotate 90 degrees counter clockwise
            case 1:
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        board2[board.length-1 - j][i] = board[i][j];
                    }
                }
                break;
        // Rotate 180 degrees
            case 2:
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        board2[board.length-1 - i][board.length-1 - j] = board[i][j];
                    }
                }   
                break;
        // Rotate 270 degrees counter clockwise
            case 3:
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        board2[i][board.length-1 - j] = board[j][i];
                    }
                } 
                break;
        // No rotation
            default:
                return board;
        }
        return board2;
    }

// Mixes the columns of each box (smaller area which has to contain all numbers)
    private static int[][] columnSwap(int[][] board) { 
        Random rand = new Random();
        int size = (int) Math.sqrt(board.length);
        int[][] board2 = new int[board.length][board.length];
        List<Integer> columns = new ArrayList<Integer>();
        List<Integer> shuffleOrder = new ArrayList<Integer>();
        for (int a = 0; a < board.length; a+=size) {
            for (int i = a; i < a + size; i++) {
                columns.add(i);
            }
            for (int i = a; i < a + size; i++) {
                int n = rand.nextInt(columns.size());
                shuffleOrder.add(columns.get(n));
                columns.remove(n);
            }
            for (int i = a; i < a + size; i++) {
                for (int j = 0; j < board.length; j++) {
                    board2[j][i] = board[j][shuffleOrder.get(i)];
                }
            }
        }
        return board2;
    }

// Mixes the rows of each box (smaller area which has to contain all numbers)
    private static int[][] rowSwap(int[][] board) { 
        Random rand = new Random();
        int size = (int) Math.sqrt(board.length);
        int[][] board2 = new int[board.length][board.length];
        List<Integer> rows = new ArrayList<Integer>();
        List<Integer> shuffleOrder = new ArrayList<Integer>();
        for (int a = 0; a < board.length; a+=size) {
            for (int i = a; i < a + size; i++) {
                rows.add(i);
            }
            for (int i = a; i < a + size; i++) {
                int n = rand.nextInt(rows.size());
                shuffleOrder.add(rows.get(n));
                rows.remove(n);
            }
            for (int i = a; i < a + size; i++) {
                for (int j = 0; j < board.length; j++) {
                    board2[i][j] = board[shuffleOrder.get(i)][j];
                }
            }
        }
        return board2;
    }
}
