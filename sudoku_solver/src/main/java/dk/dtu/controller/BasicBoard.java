package dk.dtu.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import dk.dtu.view.MainMenu;
import dk.dtu.view.campaign.CampaignMenu;
import dk.dtu.view.SudokuBoard;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class BasicBoard {
    private static final int sizeX = 800;
    private static int gridSize = 9;
    private static int btnSize = sizeX / gridSize;
    private static double fontSize = (double) btnSize / 2;
    private static int lastClickedRow = -1;
    private static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];
    public static int[][] puzzleBoard;
    public static int[][] solvedBoard;
    public static String difficulty;
    public static boolean uniqueness = true;
    public static boolean isSamurai = false;

    // Determines wether a number should be displayed or not => 0 = not displayed,
    // everything else = displayed
    public static boolean displayNum(int row, int column, int[][] board) {
        return board[row][column] != 0;
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

                blackBorder(buttons2D, row, column);
            }
        }
    }

    // Creates the sudoku board and displays it
    public static void createSudoku(GridPane pane, int boardSize, boolean unique) {
        gridSize = (int) Math.pow(boardSize, 2);
        btnSize = sizeX / gridSize;
        fontSize = btnSize * 0.20;
        buttons2D = new SudokuButton[gridSize][gridSize];
        isSamurai = false;
        SudokuBoard.hints = 3;
        if (difficulty.equals("Custom")) {
            puzzleBoard = PuzzleGenerator.generateBigSudoku(boardSize, unique);
            if (boardSize > 5) {
                uniqueness = unique;
            } else
                uniqueness = true;

        } else {
            puzzleBoard = PuzzleGenerator.generateSudoku(difficulty);
            uniqueness = true;
        }

        solvedBoard = PuzzleGenerator.deepCopy(puzzleBoard);

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                String buttonText;
                if (displayNum(row, column, puzzleBoard)) {
                    buttonText = "" + puzzleBoard[row][column];
                } else {
                    buttonText = "";
                }

                SudokuButton Button = new SudokuButton(0);

                Button.setEditable(!displayNum(row, column, puzzleBoard));

                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);

                Button.setText(buttonText);
                Button.setStyle("-fx-text-fill: black; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");

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

    public static void createSamuraiSudoku(GridPane pane) {
        double cellSize = 30; // Size for each cell in the Samurai grid
        int gridCellSize = 9; // Each grid is a 9x9 Sudoku
        int samuraiGridSize = 21; // Size of the entire Samurai Sudoku
        gridSize = 9;
        isSamurai = true;

        // Define the positions for the 5 grids (central, top-left, top-right,
        // bottom-left, bottom-right)
        int[][] gridPositions = {
                { 6, 6 }, // Center grid
                { 0, 0 }, // Top-left grid
                { 12, 0 }, // Top-right grid
                { 0, 12 }, // Bottom-left grid
                { 12, 12 } // Bottom-right grid
        };

        // Generate the Samurai Sudoku boards
        int[][][] samuraiGrids = PuzzleGenerator.generateSamuraiSudoku();

        // Reset the buttons2D array to accommodate the entire Samurai Sudoku
        buttons2D = new SudokuButton[samuraiGridSize][samuraiGridSize];

        // Create and place the 5 grids on the pane
        for (int grid = 0; grid < samuraiGrids.length; grid++) {
            int offsetX = gridPositions[grid][0];
            int offsetY = gridPositions[grid][1];

            for (int row = 0; row < gridCellSize; row++) {
                for (int col = 0; col < gridCellSize; col++) {
                    int value = samuraiGrids[grid][row][col];
                    SudokuButton button = new SudokuButton(value);
                    button.setPrefSize(cellSize, cellSize);
                    button.setText(value == 0 ? "" : String.valueOf(value));
                    button.setStyle("-fx-text-fill: black; -fx-font-size: 10 px; -fx-font-weight: bold;");
                    button.setEditable(value == 0);
                    pane.add(button, col + offsetX, row + offsetY);

                    // Save button reference to buttons2D array
                    buttons2D[row + offsetX][col + offsetY] = button;

                    // Add event handlers
                    int finalRow = row + offsetX;
                    int finalCol = col + offsetY;

                    button.addEventFilter(KeyEvent.KEY_TYPED, event -> handleKeyPress(event, finalRow, finalCol));
                    button.setOnAction(event -> clickedButton(finalRow, finalCol));

                    // Add black border for 3x3 box separation
                    blackBorder(buttons2D, finalRow, finalCol);
                }
            }
        }
    }

    private static void clickedButton(int row, int column) {
        // Fjern tidligere highlight
        removeHighlighting();

        boolean isSamurai = buttons2D.length > 9;

        if (!isSamurai) {

            // Klassisk Sudoku highlighting
            highlightClassic(row, column);

            // Highlight den valgte knap
            if (buttons2D[row][column] != null) {
                buttons2D[row][column].setStyle(buttons2D[row][column].getStyle()
                        + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #7997b3, #7997b3);");
            }

            // Opdater de sidste valgte række og kolonne
            lastClickedRow = row;
            lastClickedColumn = column;
        }
    }

    private static void highlightClassic(int row, int column) {
        // Highlight den valgte række
        for (int c = 0; c < gridSize; c++) {
            if (buttons2D[row][c] != null) {
                buttons2D[row][c].setStyle(buttons2D[row][c].getStyle()
                        + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
            }
        }

        // Highlight den valgte kolonne
        for (int r = 0; r < gridSize; r++) {
            if (buttons2D[r][column] != null) {
                buttons2D[r][column].setStyle(buttons2D[r][column].getStyle()
                        + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
            }
        }

        // Highlight den 3x3 boks
        int boxStartRow = row - row % 3;
        int boxStartColumn = column - column % 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartColumn; c < boxStartColumn + 3; c++) {
                if (buttons2D[r][c] != null) {
                    buttons2D[r][c].setStyle(buttons2D[r][c].getStyle()
                            + "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);");
                }
            }
        }
    }

    private static void removeHighlighting() {
        if (lastClickedRow != -1 && lastClickedColumn != -1) {
            // Fjern highlight fra den sidste valgte række
            for (int c = 0; c < buttons2D.length; c++) {
                if (buttons2D[lastClickedRow][c] != null) {
                    buttons2D[lastClickedRow][c].setStyle(
                            buttons2D[lastClickedRow][c].getStyle().replace(
                                    "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                                    ""));
                }
            }

            // Fjern highlight fra den sidste valgte kolonne
            for (int r = 0; r < buttons2D.length; r++) {
                if (buttons2D[r][lastClickedColumn] != null) {
                    buttons2D[r][lastClickedColumn].setStyle(
                            buttons2D[r][lastClickedColumn].getStyle().replace(
                                    "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                                    ""));
                }
            }

            // Fjern highlight fra den 3x3 boks
            for (int r = 0; r < buttons2D.length; r++) {
                for (int c = 0; c < buttons2D.length; c++) {
                    if (buttons2D[r][c] != null && r >= lastClickedRow - lastClickedRow % 3
                            && r < lastClickedRow - lastClickedRow % 3 + 3
                            && c >= lastClickedColumn - lastClickedColumn % 3
                            && c < lastClickedColumn - lastClickedColumn % 3 + 3) {
                        buttons2D[r][c].setStyle(
                                buttons2D[r][c].getStyle().replace(
                                        "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #9fb6cc, #8b9fb3);",
                                        ""));
                    }
                }
            }

            // Fjern highlight fra den valgte knap
            if (buttons2D[lastClickedRow][lastClickedColumn] != null) {
                buttons2D[lastClickedRow][lastClickedColumn].setStyle(
                        buttons2D[lastClickedRow][lastClickedColumn].getStyle().replace(
                                "; -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #7997b3, #7997b3);",
                                ""));
            }
        }
    }

    private static void handleKeyPress(KeyEvent event, int row, int column) {
        // Make sure the button is editable
        if (!buttons2D[row][column].isEditable()) {
            return;
        }

        String typedCharacter = event.getCharacter();
        String currentText = "";

        if (typedCharacter.matches("[0-9]")) {
            // If the typed character is a number, add it to the buffer only if it doesn't
            // make the length more than 2
            if (solvedBoard.length < 10) {
                buttons2D[row][column].setText(typedCharacter);
                if (typedCharacter.matches("\\d{1,2}")) {
                    // If the input is a valid number, update the board
                    solvedBoard[row][column] = Integer.parseInt(typedCharacter);

                    // Check if the board is completed and if the placement is valid
                    boolean isCompleted = LogicSolver.isDone(solvedBoard);
                    boolean validPlacement = LogicSolver.validCheck(solvedBoard);

                    if (!validPlacement) {
                        System.out.println("Mistake made");
                        buttons2D[row][column].setStyle(
                                "-fx-text-fill: red; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                        if (SudokuBoard.lifeOn) {
                            SudokuBoard.mistakes++;
                            SudokuBoard.lifeButton.setText("Mistakes: " + SudokuBoard.mistakes + "/3");
                        }
                    } else {
                        buttons2D[row][column].setStyle(
                                "-fx-text-fill: dimgrey; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                    }

                    if (!validPlacement && SudokuBoard.mistakes == 3 && SudokuBoard.lifeOn) {
                        System.out.println("Game over");
                        SudokuBoard.lifeButton.setText("Mistakes: 3/3");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game over");
                        alert.setHeaderText("You have made 3 mistakes. Game over");
                        alert.showAndWait();
                        closeSudokuBoard();
                        MainMenu.mainMenuStage.show();
                    }

                    if (isCompleted && validPlacement) {
                        String time = SudokuBoard.finalTime;

                        // Create a new alert
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Congratulations");
                        alert.setHeaderText(
                                "Sudoku Completed! Your time was: " + time + " with " + SudokuBoard.mistakes
                                        + " mistakes");

                        // Create a new TextField and set it as the graphic for the alert
                        TextField textField = new TextField("Input name");
                        alert.getDialogPane().setContent(textField);

                        // Add two buttons
                        ButtonType saveTimeBtn = new ButtonType("Save to leaderboards");
                        ButtonType exitBtn = new ButtonType("Exit");
                        alert.getButtonTypes().setAll(saveTimeBtn, exitBtn);

                        // Show the alert and wait for the user to close it
                        Optional<ButtonType> result = alert.showAndWait();
                        // noinspection OptionalGetWithoutIsPresent
                        if (result.get() == saveTimeBtn) {
                            if (difficulty.contains(String.valueOf(CampaignMenu.currentLevel))) {
                                CampaignMenu.updateIsDone(true);
                                CampaignMenu.currentLevel++;
                                CampaignMenu.updateCurrentLevel();
                            }

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
                                    difficulty = "Custom " + (int) Math.sqrt(gridSize) + "x"
                                            + (int) Math.sqrt(gridSize);
                                }
                                pStatement.setString(3, difficulty);
                                pStatement.setInt(4, SudokuBoard.mistakes);
                                pStatement.executeUpdate();
                                conn.close();
                                if (difficulty.contains(String.valueOf(CampaignMenu.currentLevel))) {
                                    closeSudokuBoard();
                                    CampaignMenu campaignMenu = new CampaignMenu();
                                    campaignMenu.showCampaign();
                                } else {
                                    closeSudokuBoard();
                                    MainMenu.mainMenuStage.show();
                                }
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (result.get() == exitBtn) {
                            if (difficulty.contains(String.valueOf(CampaignMenu.currentLevel))) {
                                CampaignMenu.updateIsDone(true);
                                CampaignMenu.currentLevel++;
                                CampaignMenu.updateCurrentLevel();
                                closeSudokuBoard();
                                CampaignMenu campaignMenu = new CampaignMenu();
                                campaignMenu.showCampaign();
                            } else {
                                // Handle "Exit" button click here
                                closeSudokuBoard();
                                MainMenu.mainMenuStage.show();
                            }
                        }
                    }
                } else {
                    // If the input is not a valid number, clear the buffer
                    buttons2D[row][column].setText("");
                }
            }
            currentText = buttons2D[row][column].getText();
            if (currentText.length() < 2 && solvedBoard.length > 9) {
                buttons2D[row][column].setText(currentText + typedCharacter);
                currentText = currentText + typedCharacter;
            }
        } else if (typedCharacter.equals("\b")) { // Check if the backspace key was pressed
            // If the backspace key was pressed, remove the last character from the buffer
            currentText = buttons2D[row][column].getText();
            buttons2D[row][column]
                    .setStyle("-fx-text-fill: black; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
            if (!currentText.isEmpty()) {
                buttons2D[row][column].setText(currentText.substring(0, currentText.length() - 1));
                currentText = buttons2D[row][column].getText();
            }
        } else if (typedCharacter.equals("\r") && solvedBoard.length > 9) { // Check if the enter key was pressed
            // If the enter key was pressed, check the input
            String cellInput = buttons2D[row][column].getText();
            if (cellInput.matches("\\d{1,2}")) {
                // If the input is a valid number, update the board
                solvedBoard[row][column] = Integer.parseInt(cellInput);

                // Check if the board is completed and if the placement is valid
                boolean isCompleted = LogicSolver.isDone(solvedBoard);
                boolean validPlacement = LogicSolver.validCheck(solvedBoard);

                if (uniqueness && !validPlacement) {
                    System.out.println("Mistake made");
                    buttons2D[row][column]
                            .setStyle("-fx-text-fill: red; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold;");
                    if (SudokuBoard.lifeOn) {
                        SudokuBoard.mistakes++;
                        SudokuBoard.lifeButton.setText("Mistakes: " + SudokuBoard.mistakes + "/3");
                    }
                }

                if (uniqueness && !validPlacement && SudokuBoard.mistakes == 3 && SudokuBoard.lifeOn) {
                    System.out.println("Game over");
                    SudokuBoard.lifeButton.setText("Mistakes: 3/3");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game over");
                    alert.setHeaderText("You have made 3 mistakes. Game over");
                    alert.showAndWait();
                    closeSudokuBoard();
                    MainMenu.mainMenuStage.show();
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
                    // noinspection OptionalGetWithoutIsPresent
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
                                difficulty = "Custom " + (int) Math.sqrt(gridSize) + "x" + (int) Math.sqrt(gridSize);
                            }
                            pStatement.setString(3, difficulty);
                            pStatement.setInt(4, SudokuBoard.mistakes);
                            pStatement.executeUpdate();
                            conn.close();
                            closeSudokuBoard();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (result.get() == exitBtn) {
                        // Handle "Exit" button click here

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
                if (currentText.equals(buttons2D[row][column].getText())
                        || (solvedBoard.length < 10 && typedCharacter.equals(buttons2D[row][column].getText()))) {
                    if (!buttons2D[row][column].getStyle().contains("red")) { // Check if the text color is already red
                        buttons2D[row][column]
                                .setStyle("-fx-text-fill: blue; -fx-font-size: " + fontSize
                                        + "px; -fx-font-weight: bold;");
                    }
                    blackBorder(buttons2D, row, column);
                } else if (displayNum(row, column, puzzleBoard)) {
                    if (!buttons2D[row][column].getStyle().contains("red")) { // Check if the text color is already red
                        buttons2D[row][column]
                                .setStyle(
                                        "-fx-text-fill: black; -fx-font-size: " + fontSize
                                                + "px; -fx-font-weight: bold;");
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
        SudokuBoard.timeString = "00:00";
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
        if (buttons2D.length > 9 && isSamurai) {
            if (row == 2 & row == 5 & row == 9 & row == 11 & row == 14 & row == 17 & row == 20 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
            }
            if (column == 2 & column == 5 & column == 11 & column == 14 & column == 17 & column == 20 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
            }
            if (row == 8 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");
            }
            if (row == 6 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
            }
            if (row == 7 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
            }
            if (row == 9 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
            }
            if (row == 10 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
            }
            if (row == 11 & column == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
            }
            if (column == 6 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
            }
            if (column == 7 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
            }
            if (column == 9 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
            }
            if (column == 10 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
            }
            if (column == 11 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
            }
            if (column == 8 & row == 11) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");
            }
            if (column == 11 & row == 8) {
                button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");

            }
        }

    }

    public static void showHint() {
        puzzleBoard = PuzzleGenerator.originalBoard;
        // Find the first empty cell
        int row;
        int column;
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