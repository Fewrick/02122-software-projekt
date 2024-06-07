package dk.dtu.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import dk.dtu.view.MainMenu;
import dk.dtu.view.medium.SudokuBoard;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class BasicBoard {
    private static int sizeX = 800;
    private static int gridSize = 9;
    private static int btnSize = sizeX / gridSize;
    private static double fontSize = btnSize / 2;
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
                Button.setStyle("-fx-text-fill: dimgrey; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");

                buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.

                // Add event handler for button click
                int finalRow = row;
                int finalColumn = column;

                blackBorder(buttons2D, finalRow, finalColumn);
            }
        }
    }

    // Creates the sudoku board and displays it
    public static void createSudoku(GridPane pane, int boardSize, boolean unique) {
        gridSize = (int) Math.pow(boardSize, 2);
        btnSize = sizeX / gridSize;
        fontSize = btnSize * 0.15;
        buttons2D = new SudokuButton[gridSize][gridSize];
        if (difficulty.equals("Custom")) {
            puzzleBoard = PuzzleGenerator.generateBigSudoku(boardSize, unique);

        } else {
            puzzleBoard = PuzzleGenerator.generateSudoku(difficulty);

        }

        solvedBoard = PuzzleGenerator.deepCopy(puzzleBoard);

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (displayNum(row, column, puzzleBoard)) {
                    buttonText = "" + puzzleBoard[row][column];
                } else {
                    buttonText = "";
                }

                SudokuButton Button = new SudokuButton(0);

                if (displayNum(row, column, puzzleBoard)) {
                    Button.setEditable(false);
                } else {
                    Button.setEditable(true);
                }

                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);

                Button.setText(buttonText);
                Button.setStyle("-fx-text-fill: black; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");

                buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.

                // Add event handler for button click
                int finalRow = row;
                int finalColumn = column;

                Button.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                    handleKeyPress(event, finalRow, finalColumn);

                });

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

        // highlight the 3x3 box
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                if (r >= row - row % Math.sqrt(gridSize) && r < row - row % Math.sqrt(gridSize) + Math.sqrt(gridSize)
                        && c >= column - column % Math.sqrt(gridSize)
                        && c < column - column % Math.sqrt(gridSize) + Math.sqrt(gridSize)) {
                    buttons2D[r][c].setStyle(buttons2D[r][c].getStyle()
                            + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
                }
            }
        }

        // higlight the clicked button
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

            // Clear highlighting from the 3x3 box
            for (int r = 0; r < gridSize; r++) {
                for (int c = 0; c < gridSize; c++) {
                    if (r >= lastClickedRow - lastClickedRow % Math.sqrt(gridSize)
                            && r < lastClickedRow - lastClickedRow % Math.sqrt(gridSize) + Math.sqrt(gridSize)
                            && c >= lastClickedColumn - lastClickedColumn % Math.sqrt(gridSize)
                            && c < lastClickedColumn - lastClickedColumn % Math.sqrt(gridSize) + Math.sqrt(gridSize)) {
                        buttons2D[r][c].setStyle(
                                buttons2D[r][c].getStyle().replace(
                                        "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                                        ""));
                    }
                }
            }

            // Clear highlighting from the clicked button
            buttons2D[lastClickedRow][lastClickedColumn].setStyle(
                    buttons2D[lastClickedRow][lastClickedColumn].getStyle().replace(
                            "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #7997b3, #7997b3);",
                            ""));
        }
    }

    private static void handleKeyPress(KeyEvent event, int row, int column) {
        // Make sure the button is editable
    if (!buttons2D[row][column].isEditable()) {
        return;
    }

    String typedCharacter = event.getCharacter();

    if (typedCharacter.matches("[0-9]")) {
        // If the typed character is a number, add it to the buffer only if it doesn't make the length more than 2
        String currentText = buttons2D[row][column].getText();
        if (currentText.length() < 2) {
            buttons2D[row][column].setText(currentText + typedCharacter);
        }
    } else if (typedCharacter.equals("\b")) { // Check if the backspace key was pressed
        // If the backspace key was pressed, remove the last character from the buffer
        String currentText = buttons2D[row][column].getText();
        if (currentText.length() > 0) {
            buttons2D[row][column].setText(currentText.substring(0, currentText.length() - 1));
        }
    } else if (typedCharacter.equals("\r")) { // Check if the enter key was pressed
        // If the enter key was pressed, check the input
        String cellInput = buttons2D[row][column].getText();
        if (cellInput.matches("\\d{1,2}")) {
            // If the input is a valid number, update the board
            solvedBoard[row][column] = Integer.parseInt(cellInput);

            // Check if the board is completed and if the placement is valid
            boolean isCompleted = LogicSolver.isDone(solvedBoard);
            boolean validPlacement = LogicSolver.validCheck(solvedBoard);

            if (!validPlacement) {
                System.out.println("Mistake made");
                buttons2D[row][column].setStyle("-fx-text-fill: red; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                if (SudokuBoard.lifeOn == true) {
                    SudokuBoard.mistakes++;
                    SudokuBoard.lifeButton.setText("Mistakes: " + SudokuBoard.mistakes + "/3");
                }
            }

            if (SudokuBoard.mode == SudokuBoard.Mode.NUMBER) {
                buttons2D[row][column].setDraft(false);

                if (!validPlacement && SudokuBoard.mistakes == 3 && SudokuBoard.lifeOn == true) {
                    System.out.println("Game over");
                    SudokuBoard.lifeButton.setText("Mistakes: 3/3");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game over");
                    alert.setHeaderText("You have made 3 mistakes. Game over");
                    alert.showAndWait();
                    System.exit(0);
                }

            } else if (SudokuBoard.mode == SudokuBoard.Mode.DRAFT) {
                buttons2D[row][column].setDraft(true);
                buttons2D[row][column].setStyle("-fx-text-fill: darksalmon; -fx-font-size: 1.5em; -fx-font-weight: bold;");
            }

                if (isCompleted && validPlacement) {
                    String time = SudokuBoard.finalTime;

                    // Create a new alert
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Congratulations");
                    alert.setHeaderText(
                            "Sudoku Completed! Your time was: " + time + " with " + SudokuBoard.mistakes + " mistakes");

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

                        String query = "INSERT INTO leaderboard (name, time, difficulty, mistakes) VALUES (?, ?, ?, ?)";
                        // Connect to the database
                        try (Connection conn = DriverManager.getConnection(
                                "jdbc:postgresql://cornelius.db.elephantsql.com:5432/bvdlelci", "bvdlelci",
                                "B1QrdKqxmTmhI1qgLU-XnZvRoIdC8fzq");
                                PreparedStatement pStatement = conn.prepareStatement(query)) {

                            // Insert the name, time, and difficulty into the leaderboard table
                            pStatement.setString(1, name);
                            pStatement.setString(2, time);
                            if (difficulty.equals("Custom")) {
                                difficulty = "Custom " + gridSize + "x" + gridSize;
                            }
                            pStatement.setString(3, difficulty);
                            pStatement.setInt(4, SudokuBoard.mistakes);
                            pStatement.executeUpdate();
                            conn.close();
                            closeSudokuBoard();
                            MainMenu.mainMenuStage.show();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (result.get() == exitBtn) {
                        // Handle "Exit" button click here
                        closeSudokuBoard();
                        MainMenu.mainMenuStage.show();
                    }
                }
            } else {
                // If the input is not a valid number, clear the buffer
                buttons2D[row][column].setText("");
            }
        }

        event.consume();

        for (row = 0; row < gridSize; row++) {
            for (column = 0; column < gridSize; column++) {
    
                if (buttons2D[row][column].isDraft()) {
                    buttons2D[row][column]
                            .setStyle("-fx-text-fill: darksalmon; -fx-font-size: 0.5px; -fx-font-weight: bold;");
                    blackBorder(buttons2D, row, column);
                } else if (typedCharacter.equals(buttons2D[row][column].getText())) {
                    if (!buttons2D[row][column].getStyle().contains("red")) { // Check if the text color is already red
                        buttons2D[row][column]
                                .setStyle("-fx-text-fill: blue; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                    }
                    blackBorder(buttons2D, row, column);
                } else if (displayNum(row, column, puzzleBoard)) {
                    if (!buttons2D[row][column].getStyle().contains("red")) { // Check if the text color is already red
                        buttons2D[row][column]
                                .setStyle(
                                        "-fx-text-fill: black; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                    }
                    blackBorder(buttons2D, row, column);
                } else {
                    if (!buttons2D[row][column].getStyle().contains("red")) { // Check if the text color is already red
                        buttons2D[row][column]
                                .setStyle("-fx-text-fill: dimgrey; -fx-font-size: " + fontSize
                                        + "px; -fx-font-weight: bold;");
                    }
                    blackBorder(buttons2D, row, column);
                }
    
            }
        }
    }

    private static void closeSudokuBoard() {
        SudokuBoard.boardStage.close();
        SudokuBoard.bottom.getChildren().clear();
        SudokuBoard.pane.getChildren().clear();
        SudokuBoard.borderPane.getChildren().clear();
        SudokuBoard.topVbox.getChildren().clear();
        SudokuBoard.leftVbox.getChildren().clear();
        SudokuBoard.rightVbox.getChildren().clear();

        SudokuBoard.timeline.stop();
        SudokuBoard.timeline.getKeyFrames().clear();
        SudokuBoard. timeString = "00:00";
        SudokuBoard.seconds = 0;
        SudokuBoard.minutes = 0;
        SudokuBoard.timer.setText("Timer: " + SudokuBoard.timeString);
        SudokuBoard.mistakes = 0;
    }

    public static void blackBorder(SudokuButton[][] buttons, int row, int column) {
        SudokuButton button = buttons[row][column];

        // Add black borders to separate 3x3 boxes
        if ((column + 1) % Math.sqrt(gridSize) == 0 && column + 1 != gridSize) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
        }
        if ((row + 1) % Math.sqrt(gridSize) == 0 && row + 1 != gridSize) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
        }
        if ((column + 1) % Math.sqrt(gridSize) == 0 && column != gridSize - 1 && (row + 1) % Math.sqrt(gridSize) == 0
                && row != gridSize - 1) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");
        }
    }

    public static void showHint() {
        puzzleBoard = PuzzleGenerator.originalBoard;
        // Find the first empty cell
        int row = 0;
        int column = 0;
        boolean found = false;

        for (row = 0; row < gridSize; row++) {
            for (column = 0; column < gridSize; column++) {
                if (solvedBoard[row][column] == 0) {
                    found = true;
                    break;
                }
            }
            if (found) {
                // choose one empty cell and show the value from the solvedboard
                // take the empty cell and show the value from the solvedboard
                // make the text color black and set the button to not editable
                buttons2D[row][column].setText("" + puzzleBoard[row][column]);
                buttons2D[row][column]
                        .setStyle("-fx-text-fill: black; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                buttons2D[row][column].setEditable(false);
                solvedBoard[row][column] = puzzleBoard[row][column];
                blackBorder(buttons2D, row, column);
                break;
            }
        }
    }

}