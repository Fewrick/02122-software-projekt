package dk.dtu.view.easy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import dk.dtu.controller.EasyChecker;
import dk.dtu.controller.SudokuButton;
import dk.dtu.view.medium.SudokuBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class BasicBoard4x4 {
    static int sizeX = 600; // Justeret for et mindre vindue
    static int sizeY = 600;
    static int gridSize = 4; // 4x4 grid
    static int btnSize = sizeX / gridSize; // Størrelse af hver knap
    static int lastClickedRow = -1;
    static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];
    public static int[][] puzzleBoard;
    public static int[][] solvedBoard;
    public static String difficulty;

    public static boolean displayNum(int row, int column) {
        // Antager at du har en tilsvarende lagringsmekanisme for boardets tilstand
        // Returnerer true hvis der skal vises et nummer, ellers false
        return Board4x4.grid[row][column] != 0;
    }

    public static void createSudoku(GridPane pane) {
        /*
         * puzzleBoard = PuzzleGenerator.GenerateSudoku(difficulty);
         * solvedBoard = PuzzleGenerator.deepCopy(puzzleBoard);
         */

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

                    solvedBoard[finalRow][finalColumn] = Integer.parseInt(event.getCharacter());
                    Boolean isCompleted = EasyChecker.boardCompleted(solvedBoard);
                    if (isCompleted) {
                        String time = SudokuBoard.finalTime;

                        // Create a new alert
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Congratulations");
                        alert.setHeaderText("Sudoku Completed! Your time was: " + time);

                        // Create a new TextField and set it as the graphic for the alert
                        TextField textField = new TextField("Input name");
                        alert.getDialogPane().setContent(textField);

                        // Add two buttons
                        ButtonType saveTimeBtn = new ButtonType("Save to leaderboards");
                        ButtonType exitBtn = new ButtonType("Exit");
                        alert.getButtonTypes().setAll(saveTimeBtn, exitBtn);

                        // Show the alert and wait for the user to close it
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == saveTimeBtn) {
                            String name = textField.getText();

                            ;

                            // Connect to the database
                            try (Connection conn = DriverManager.getConnection(
                                    "jdbc:postgresql://cornelius.db.elephantsql.com:5432/bvdlelci", "bvdlelci",
                                    "B1QrdKqxmTmhI1qgLU-XnZvRoIdC8fzq");
                                    Statement stmt = conn.createStatement()) {

                                // Insert the name, time, and difficulty into the leaderboard table
                                stmt.executeUpdate("INSERT INTO leaderboard (name, time, difficulty) VALUES ('" + name
                                        + "', '" + time + "', '" + difficulty + "')");
                                conn.close();
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (result.get() == exitBtn) {
                            // Handle "Exit" button click here
                        }
                    }
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

        // highlight the 2x2 box
        int boxRow = row / 2;
        int boxColumn = column / 2;
        for (int r = boxRow * 2; r < boxRow * 2 + 2; r++) {
            for (int c = boxColumn * 2; c < boxColumn * 2 + 2; c++) {
                buttons2D[r][c].setStyle(buttons2D[r][c].getStyle()
                        + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
            }
        }

        // highlight the clicked button
        buttons2D[row][column].setStyle(buttons2D[row][column].getStyle()
                + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #7997b3, #7997b3);");

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

            // clear the highlighting from the 2x2 box
            int boxRow = lastClickedRow / 2;
            int boxColumn = lastClickedColumn / 2;
            for (int r = boxRow * 2; r < boxRow * 2 + 2; r++) {
                for (int c = boxColumn * 2; c < boxColumn * 2 + 2; c++) {
                    buttons2D[r][c].setStyle(buttons2D[r][c].getStyle().replace(
                            "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                            ""));
                }
            }

            // clear the highlighting from the clicked button
            buttons2D[lastClickedRow][lastClickedColumn].setStyle(buttons2D[lastClickedRow][lastClickedColumn]
                    .getStyle()
                    .replace(
                            "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #7997b3, #7997b3);",
                            ""));
        }
    }

    private static void handleKeyPress(KeyEvent event, int row, int column) {
        String typedCharacter = event.getCharacter();

        // Check if the key is a digit from 1 to 4
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
                    + "; -fx-border-width: 0 3 0 0; -fx-border-color: lightgrey black lightgrey lightgrey;");
        }
        if ((row + 1) % 2 == 0 && row + 1 != gridSize) {
            button.setStyle(button.getStyle()
                    + "; -fx-border-width: 0 0 3 0; -fx-border-color: lightgrey lightgrey black lightgrey;");
        }
        if ((column + 1) % 2 == 0 && column != gridSize - 1 && (row + 1) % 2 == 0 && row != gridSize - 1) {
            button.setStyle(button.getStyle()
                    + "; -fx-border-width: 0 3 3 0; -fx-border-color: lightgrey black black lightgrey;");
        }

    }
}
