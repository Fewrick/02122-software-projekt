package dk.dtu.controller;

import dk.dtu.view.medium.Board;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;

public class BasicBoard {
    private static int sizeX = 810;
    private static int gridSize = 9;
    private static int btnSize = sizeX / gridSize;
    private static int lastClickedRow = -1;
    private static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];
    public static String[][] board;
    private static String buttonText;

    // Determines wether a number should be displayed or not => 0 = not displayed, everything else = displayed
    public static boolean displayNum(int row, int column, String[][] board) {
        if (board[row][column].equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    // Shows the solution to the puzzle by pulling the original board from the PuzzleGenerator class
    public static void showSolution(GridPane pane) {
        board = PuzzleGenerator.originalBoard;

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                SudokuButton Button = new SudokuButton(0);
                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);

                Button.setText("" + board[row][column]);
                Button.setStyle("-fx-text-fill: dimgrey; -fx-font-size: 2.0em; -fx-font-weight: bold;");

                buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.

                // Add event handler for button click
                int finalRow = row;
                int finalColumn = column;

                blackBorder(buttons2D, finalRow, finalColumn);
            }
        }
    }

    // Creates the sudoku board and displays it
    public static void createSudoku(GridPane pane) {
        board = PuzzleGenerator.GenerateSudoku();

        
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (displayNum(row, column, board)) {
                    buttonText = "" + board[row][column];
                    } else {
                    buttonText = "";
                }
                SudokuButton Button = new SudokuButton(0);
                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);

                Button.setText(buttonText);
                Button.setStyle("-fx-text-fill: black; -fx-font-size: 2.0em; -fx-font-weight: bold;");

                buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.

                // Add event handler for button click
                int finalRow = row;
                int finalColumn = column;

                Button.addEventFilter(KeyEvent.KEY_TYPED, event -> handleKeyPress(event, finalRow, finalColumn));
                Button.setOnAction(event -> clickedButton(finalRow, finalColumn));

                blackBorder(buttons2D, finalRow, finalColumn);
            }
        }
    }

    private static void clickedButton(int row, int column) {
        // Clear highlighting from the previously clicked row and column
        removeHighlighting();

        // Highlight the entire row
        for (int c = 0; c < gridSize; c++) {
            buttons2D[row][c].setStyle(buttons2D[row][c].getStyle()
                    + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
        }

        // Highlight the entire column
        for (int r = 0; r < gridSize; r++) {
            buttons2D[r][column].setStyle(buttons2D[r][column].getStyle()
                    + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
        }

        // Update the last clicked row and column
        lastClickedRow = row;
        lastClickedColumn = column;
    }

    private static void removeHighlighting() {
        if (lastClickedRow != -1 && lastClickedColumn != -1) {
            // Clear highlighting from the last clicked row
            for (int c = 0; c < gridSize; c++) {
                buttons2D[lastClickedRow][c].setStyle(
                        buttons2D[lastClickedRow][c].getStyle().replace(
                                "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                                ""));
            }

            // Clear highlighting from the last clicked column
            for (int r = 0; r < gridSize; r++) {
                buttons2D[r][lastClickedColumn].setStyle(
                        buttons2D[r][lastClickedColumn].getStyle().replace(
                                "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                                ""));
            }
        }
    }

    private static void handleKeyPress(KeyEvent event, int row, int column) {
        String typedCharacter = event.getCharacter();

        // Check if the key is a digit from 1 to 9
        if (typedCharacter.matches("[1-9]")) {
            // If the button is empty, set its text to the number
            if (displayNum(row, column, board)) {
                buttonText = "" + Board.gridComplete[row][column];
            } else {
                buttons2D[row][column].setText(typedCharacter);

            }
            event.consume();
        }

        for (row = 0; row < gridSize; row++) {
            for (column = 0; column < gridSize; column++) {
                if (typedCharacter.equals(buttons2D[row][column].getText())) {
                    buttons2D[row][column]
                            .setStyle("-fx-text-fill: darkblue; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                    blackBorder(buttons2D, row, column);
                } else {
                    if (displayNum(row, column, board)) {
                        buttons2D[row][column]
                            .setStyle("-fx-text-fill: black; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                    blackBorder(buttons2D, row, column);
                    }
                    else {
                    buttons2D[row][column]
                            .setStyle("-fx-text-fill: dimgrey; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                    blackBorder(buttons2D, row, column);
                    }
                }
            }
        }
    }

    private static void blackBorder(SudokuButton[][] buttons, int row, int column) {
        SudokuButton button = buttons[row][column];

        // Add black borders to separate 3x3 boxes
        if ((column + 1) % 3 == 0 && column + 1 != gridSize) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
        }
        if ((row + 1) % 3 == 0 && row + 1 != gridSize) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
        }
        if ((column + 1) % 3 == 0 && column != gridSize - 1 && (row + 1) % 3 == 0 && row != gridSize - 1) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");
        }
    }

}