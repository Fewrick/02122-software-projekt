package dk.dtu.view.easy;

import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.SudokuButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class BasicBoard4x4 {
    static int sizeX = 400; // Justeret for et mindre vindue
    static int sizeY = 400;
    static int gridSize = 4; // 4x4 grid
    static int btnSize = sizeX / gridSize; // Størrelse af hver knap
    static int lastClickedRow = -1;
    static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    public static boolean displayNum(int row, int column) {
        // Antager at du har en tilsvarende lagringsmekanisme for boardets tilstand
        // Returnerer true hvis der skal vises et nummer, ellers false
        return Board4x4.grid[row][column] != 0;
    }

    public static void createSudoku(GridPane pane) {
        pane.getChildren().clear(); // Ryd panelet for eksisterende komponenter

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                String buttonText = displayNum(row, column) ? String.valueOf(Board4x4.grid[row][column]) : "";
                SudokuButton button = new SudokuButton(0); // Antager en tilsvarende konstruktør eksisterer
                button.setPrefSize(btnSize, btnSize);

                pane.add(button, column, row);

                button.setText(buttonText);
                button.setStyle("-fx-text-fill: black; -fx-font-size: 2em; -fx-font-weight: bold;");

                buttons2D[row][column] = button;

                // Tilføj event handler for klik på knappen
                int finalRow = row;
                int finalColumn = column;

                button.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                    handleKeyPress(event, finalRow, finalColumn);
                });

                // Tilføj sorte kanter for at adskille 2x2 bokse
                if ((column + 1) % 2 == 0 && column + 1 != gridSize) {
                    button.setStyle(button.getStyle()
                            + "; -fx-border-width: 0 2 0 0; -fx-border-color: lightgrey black lightgrey lightgrey;");
                }
                if ((row + 1) % 2 == 0 && row + 1 != gridSize) {
                    button.setStyle(button.getStyle()
                            + "; -fx-border-width: 0 0 2 0; -fx-border-color: lightgrey lightgrey black lightgrey;");
                }
                if ((column + 1) % 2 == 0 && column != gridSize - 1 && (row + 1) % 2 == 0 && row != gridSize - 1) {
                    button.setStyle(button.getStyle()
                            + "; -fx-border-width: 0 2 2 0; -fx-border-color: lightgrey black black lightgrey;");
                }

                button.setOnAction(event -> clickedButton(finalRow, finalColumn));
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
        if (typedCharacter.matches("[1-4]")) {
            // If the button is empty, set its text to the number
            if (displayNum(row, column)) {
            } else {
                buttons2D[row][column].setText(typedCharacter);

            }
            event.consume();
        }

        for (row = 0; row < gridSize; row++) {
            for (column = 0; column < gridSize; column++) {
                if (typedCharacter.equals(buttons2D[row][column].getText())) {
                    buttons2D[row][column]
                            .setStyle("-fx-text-fill: blue; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                    blackBorder(buttons2D, row, column);
                } else {
                    if (displayNum(row, column)) {
                        buttons2D[row][column]
                                .setStyle("-fx-text-fill: black; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                        blackBorder(buttons2D, row, column);
                    } else {
                        buttons2D[row][column]
                                .setStyle("-fx-text-fill: dimgrey; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                        blackBorder(buttons2D, row, column);
                    }
                }
            }
        }
    }

    public static void blackBorder(SudokuButton[][] buttons, int row, int column) {
        SudokuButton button = buttons[row][column];

        // Tilføj sorte kanter for at adskille 2x2 bokse
        if ((column + 1) % 2 == 0 && column + 1 != gridSize) {
            button.setStyle(button.getStyle()
                    + "; -fx-border-width: 0 2 0 0; -fx-border-color: lightgrey black lightgrey lightgrey;");
        }
        if ((row + 1) % 2 == 0 && row + 1 != gridSize) {
            button.setStyle(button.getStyle()
                    + "; -fx-border-width: 0 0 2 0; -fx-border-color: lightgrey lightgrey black lightgrey;");
        }
        if ((column + 1) % 2 == 0 && column != gridSize - 1 && (row + 1) % 2 == 0 && row != gridSize - 1) {
            button.setStyle(button.getStyle()
                    + "; -fx-border-width: 0 2 2 0; -fx-border-color: lightgrey black black lightgrey;");
        }

    }
}
