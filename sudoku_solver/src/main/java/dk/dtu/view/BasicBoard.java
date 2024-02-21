package dk.dtu.view;

import dk.dtu.controller.SudokuButton;
import javafx.scene.layout.GridPane;

public class BasicBoard {
    static int sizeX = 810;
    static int sizeY = 810;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static int lastClickedRow = -1;
    static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    public static boolean displayNum(int row, int column) {
        if (Board.grid[row][column] == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void basicSudoku(GridPane pane) {

        String buttonText;
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (displayNum(row, column)) {
                    buttonText = "" + Board.grid[row][column];
                } else {
                    buttonText = "";
                }
                SudokuButton Button = new SudokuButton(0);
                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);

                Button.setText(buttonText);
                Button.setStyle("-fx-text-fill: blue; -fx-font-size: 2.0em; -fx-font-weight: bold;");

                // Add black borders to separate 3x3 boxes
                if ((column + 1) % 3 == 0 && column + 1 != gridSize) {
                    Button.setStyle(Button.getStyle() + "; -fx-border-color: grey; -fx-border-width: 0 2px 0 0;");
                }
                if ((row + 1) % 3 == 0 && row + 1 != gridSize) {
                    Button.setStyle(Button.getStyle() + "; -fx-border-color: grey; -fx-border-width: 0 0 2px 0;");
                }

                if ((column + 1) % 3 == 0 && column != gridSize - 1 && (row + 1) % 3 == 0 && row != gridSize - 1) {
                    Button.setStyle(Button.getStyle() + "; -fx-border-color: grey; -fx-border-width: 0 2px 2px 0;");
                }

                buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.

                // Add event handler for button click
                int finalRow = row;
                int finalColumn = column;
                Button.setOnAction(event -> clickedButton(finalRow, finalColumn));
            }
        }
    }

    private static void clickedButton(int row, int column) {
        // Clear highlighting from the previously clicked row and column
        removeHighlighting();

        // Highlight the entire row
        for (int c = 0; c < gridSize; c++) {
            buttons2D[row][c].setStyle(buttons2D[row][c].getStyle() + "; -fx-background-color: lightgrey;");
        }

        // Highlight the entire column
        for (int r = 0; r < gridSize; r++) {
            buttons2D[r][column].setStyle(buttons2D[r][column].getStyle() + "; -fx-background-color: lightgrey;");
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
                        buttons2D[lastClickedRow][c].getStyle().replace("; -fx-background-color: lightgrey;", ""));
            }

            // Clear highlighting from the last clicked column
            for (int r = 0; r < gridSize; r++) {
                buttons2D[r][lastClickedColumn].setStyle(
                        buttons2D[r][lastClickedColumn].getStyle().replace("; -fx-background-color: lightgrey;", ""));
            }
        }
    }
}