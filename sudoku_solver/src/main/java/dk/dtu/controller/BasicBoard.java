package dk.dtu.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import dk.dtu.view.medium.Board;
import dk.dtu.view.medium.SudokuBoard;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class BasicBoard {
    private static int sizeX = 810;
    private static int gridSize = 9;
    private static int btnSize = sizeX / gridSize;
    private static int lastClickedRow = -1;
    private static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];
    public static int[][] puzzleBoard;
    public static int[][] solvedBoard;
    public static String difficulty;

    private static String buttonText;

    // Determines wether a number should be displayed or not => 0 = not displayed,
    // everything else = displayed
    public static boolean displayNum(int row, int column, int[][] board) {
        if (board[row][column] == 0) {
            return false;
        } else {
            return true;
        }
    }

    // Shows the solution to the puzzle by pulling the original board from the
    // PuzzleGenerator class
    public static void showSolution(GridPane pane) {
        puzzleBoard = PuzzleGenerator.originalBoard;

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                SudokuButton Button = new SudokuButton(0);
                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);

                Button.setText("" + puzzleBoard[row][column]);
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
        puzzleBoard = PuzzleGenerator.GenerateSudoku();
        solvedBoard = PuzzleGenerator.deepCopy(puzzleBoard);

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (displayNum(row, column, puzzleBoard)) {
                    buttonText = "" + puzzleBoard[row][column];
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

                Button.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                    handleKeyPress(event, finalRow, finalColumn);

                    // Update the board with the new value
                    solvedBoard[finalRow][finalColumn] = Integer.parseInt(event.getCharacter());                    Boolean isCompleted = Checker.boardCompleted(solvedBoard);
                    if (SudokuBoard.lifeOn == true) {
                        if (Checker.mistakeMade(finalRow, finalColumn, solvedBoard) && SudokuBoard.mistakes < 3) {
                            System.out.println("Mistake made");
                            SudokuBoard.mistakes++;
                            SudokuBoard.lifeButton.setText("Mistakes: " + SudokuBoard.mistakes + "/3");
                            Button.setStyle("-fx-text-fill: red; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                        } else if (Checker.mistakeMade(finalRow, finalColumn, solvedBoard) && SudokuBoard.mistakes == 3) {
                            System.out.println("Game over");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Game over");
                            alert.setHeaderText("You have made 3 mistakes. Game over");
                            alert.showAndWait();
                            System.exit(0);
                        }
                    }

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
                            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://cornelius.db.elephantsql.com:5432/bvdlelci", "bvdlelci", "B1QrdKqxmTmhI1qgLU-XnZvRoIdC8fzq");
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

                Button.setOnAction(event -> clickedButton(finalRow, finalColumn));

                blackBorder(buttons2D, finalRow, finalColumn);
            }
        }
    }

    private static void clickedButton(int row, int column) {
        // Clear highlighting from the previously clicked row and column
        removeHighlighting();

        //Highlight the entire row
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
            if (displayNum(row, column, puzzleBoard)) {
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
                            .setStyle("-fx-text-fill: blue; -fx-font-size: 2.0em; -fx-font-weight: bold;");
                    blackBorder(buttons2D, row, column);
                } else {
                    if (displayNum(row, column, puzzleBoard)) {
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