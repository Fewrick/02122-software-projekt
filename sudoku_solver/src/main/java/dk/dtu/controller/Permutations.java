package dk.dtu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Permutations {
    
    public static String[][] shuffle(String[][] board) {
        return rowSwap(columnSwap(rotate(flipVertical(flipHorizontal(numberExchange(board))))));
    }

// Replaces all numbers in the sudoku with a different number (i.e. all 1 = 5, all 2 = 3, ...)
    private static String[][] numberExchange(String[][] board) {
        Random rand = new Random();
        List<String> initialNumbers = new ArrayList<String>();
        List<Integer> orderRandomizer = new ArrayList<Integer>();
        List<Integer> replacementNumbers = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {
            orderRandomizer.add(i+1);
            initialNumbers.add(Integer.toString(i+1));
        }
        for (int i = 0; i < board.length; i++) {
            int n = rand.nextInt(orderRandomizer.size());
            replacementNumbers.add(orderRandomizer.get(n));
            orderRandomizer.remove(n);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                for (int k = 0; k < board.length; k++) {
                    if (initialNumbers.get(k).equals(board[i][j])) {
                        board[i][j] = Integer.toString(replacementNumbers.get(k));
                        break;
                    }
                }
            }
        }
        return board;
    }

// Flips around a vertical axis.
    private static String[][] flipVertical (String[][] board) {
    // Uncomment below, and remove "int flip" from function name, the function then flips at random.
        Random rand = new Random();
        int flip = rand.nextInt(2);

        String[][] board2 = new String[board.length][board.length];
        if (flip == 1){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board2[i][board.length-1 - j] = board[i][j];
                }
            }
        } else return board;
        return board2;
    }

// Flips around a horizontal axis.
    private static String[][] flipHorizontal (String[][] board) {
    // Uncomment below, and remove "int flip" from function name, the function then flips at random.
        Random rand = new Random();
        int flip = rand.nextInt(2);

        String[][] board2 = new String[board.length][board.length];
        if (flip == 1){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board2[board.length-1 - j][i] = board[j][i];
                }
            }
        } else return board;
        return board2;
    }

// Rotates 90 (1), 180 (2) or 270 (3) degrees
    private static String[][] rotate(String[][] board){
    // Uncomment below, and remove "int rot" from function name, and the function picks a random rotation.
        Random rand = new Random();
        int rot = rand.nextInt(4);

        String[][] board2 = new String[board.length][board.length];
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
    private static String[][] columnSwap(String[][] board) { 
        Random rand = new Random();
        int size = (int) Math.sqrt(board.length);
        String[][] board2 = new String[board.length][board.length];
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
    private static String[][] rowSwap(String[][] board) { 
        Random rand = new Random();
        int size = (int) Math.sqrt(board.length);
        String[][] board2 = new String[board.length][board.length];
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
