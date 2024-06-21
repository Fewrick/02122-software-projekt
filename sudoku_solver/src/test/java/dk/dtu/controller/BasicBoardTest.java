package dk.dtu.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicBoardTest {

  private int row;
  private int column;
  private int[][] board;

  @Test
  public void testDisplayNumWhenZero() {
    row = 2;
    column = 3;
    board = new int[9][9];
    board[row][column] = 0;
    boolean result = BasicBoard.displayNum(row, column, board);

    assertEquals(false, result, "For 0, displayNum() should return false.");
  }

  @Test
  public void testDisplayNumWhenNonZero() {
    row = 2;
    column = 3;
    board = new int[9][9];
    board[row][column] = 5;
    boolean result = BasicBoard.displayNum(row, column, board);

    assertEquals(true, result, "For non-zero, displayNum() should return true.");
  }

  @Test
  public void testDisplayNumWhenOutsideBoard() {
    row = 9;
    column = 0;
    board = new int[9][9];
    try {
      boolean result = BasicBoard.displayNum(row, column, board);
    } catch (ArrayIndexOutOfBoundsException e) {
      String expectedMessage = "Index 9 out of bounds for length 9";
      assertEquals(expectedMessage, e.getMessage());
    }
  }
}
