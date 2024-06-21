package dk.dtu.view;

import javafx.util.Duration;
import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.SudokuButton;
import dk.dtu.view.campaign.CampaignMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuBoard extends Application {
    public static String returnContext = "mainMenu";

    public static Stage boardStage = new Stage();
    static int sizeX = 800;
    static int sizeY = 800;
    public static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    public static int mistakes = 0;
    public static int hints = 3;
    public static Boolean lifeOn = true;
    public static boolean unique = false;

    static Button solveSudoku = new Button("Solution");
    public Button backtoMenu = new Button("Back to Menu");
    static Button hint = new Button("Hint");
    public static Button lifeButton = new Button("Mistakes: " + mistakes + "/3");
    public static Button hintButton = new Button("Hints: " + hints + "/3");
    public static Button timer = new Button(updateTimeString());
    static Button applyNumberMode = new Button("Number Mode");
    static Button draftMode = new Button("Draft Mode");

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    public static BorderPane borderPane;
    public static GridPane pane = new GridPane();
    public static VBox leftVbox = new VBox();
    public static VBox rightVbox = new VBox();
    public static HBox topVbox = new HBox();
    public static HBox bottom = new HBox();

    // timer variables
    public static int seconds = 0;
    public static int minutes = 0;
    public static String timeString = "00:00";
    public static String finalTime = "00:00";
    public static Timeline timeline;

    public SudokuBoard(int boardSize) {
        SudokuBoard.gridSize = boardSize;
        buttons2D = new SudokuButton[gridSize][gridSize];
    }

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();

        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        borderPane.setLeft(leftVbox);
        borderPane.setRight(rightVbox);
        borderPane.setTop(topVbox);

        // Create the sudoku puzzle
        System.out.println("Generating sudoku board...");
        BasicBoard.createSudoku(pane, gridSize, unique);
        System.out.println("Sudoku board generated");

        // Constructs pane
        topVbox.setPrefHeight(sizeY / gridSize - 20);
        leftVbox.setPrefWidth(sizeX / gridSize - 200);
        rightVbox.setPrefWidth(sizeX / gridSize - 200);
        pane.setStyle("-fx-background-color: lightgrey;");

        String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
                + "-fx-font-size: 1.3em; -fx-min-width: 130px; -fx-min-height: 40px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        solveSudoku.setStyle(buttonStyle);
        backtoMenu.setStyle(buttonStyle);
        hint.setStyle(buttonStyle);
        timer.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em;");

        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        solveSudoku.setOnMouseEntered(e -> solveSudoku.setStyle(buttonStyle + hoverStyle));
        solveSudoku.setOnMouseExited(e -> solveSudoku.setStyle(buttonStyle));
        backtoMenu.setOnMouseEntered(e -> backtoMenu.setStyle(buttonStyle + hoverStyle));
        backtoMenu.setOnMouseExited(e -> backtoMenu.setStyle(buttonStyle));
        hint.setOnMouseEntered(e -> hint.setStyle(buttonStyle + hoverStyle));
        hint.setOnMouseExited(e -> hint.setStyle(buttonStyle));

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY / 9);
        bottom.getChildren().addAll(backtoMenu, hint, solveSudoku);
        bottom.setAlignment(Pos.CENTER);
        HBox.setMargin(backtoMenu, new javafx.geometry.Insets(0, 20, 0, 40));
        HBox.setMargin(hint, new javafx.geometry.Insets(0, 20, 0, 0));
        HBox.setMargin(solveSudoku, new javafx.geometry.Insets(0, 40, 0, 0));

        topVbox.setPrefHeight(sizeY / 9);
        topVbox.getChildren().addAll(timer);
        HBox.setMargin(timer, new javafx.geometry.Insets(40, 490, 0, 70));

        // HBox.setMargin(hintButton, new javafx.geometry.Insets(40, 0, 0, 0));
        // topVbox.getChildren().add(hintButton);
        // hintButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em; ");
        // hintButton.setText("Hints: " + hints + "/3");
        // Life options setup
        if (lifeOn) {
            HBox.setMargin(lifeButton, new javafx.geometry.Insets(40, 0, 0, 0));
            topVbox.getChildren().add(lifeButton);
            lifeButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em; ");
            lifeButton.setText("Mistakes: " + mistakes + "/3");
        }

        // create timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            seconds++;
            if (seconds == 60) {
                minutes++;
                seconds = 0;
            }
            updateTimeString();
            timer.setText(timeString);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        // start the timer
        timeline.play();

        boardStage.show();

        // Button actions
        solveSudoku.setOnAction(arg0 -> {
            try {
                BasicBoard.showSolution(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // In SudokuBoard.java
        backtoMenu.setOnAction(arg1 -> {
            boardStage.close();  // Always close the current stage
            clearBoardResources(); // A method to clear resources and stop any running timelines

            if ("campaignMenu".equals(returnContext)) {
                CampaignMenu campaignMenu = new CampaignMenu(); // Instantiate CampaignMenu
                campaignMenu.showCampaign();  // Show campaign menu
            } else {
                timeline.stop();
                timeline.getKeyFrames().clear();
                timeString = "00:00";
                seconds = 0;
                minutes = 0;
                timer.setText("Timer: " + timeString);
                mistakes = 0;
                hints = 3;
                MainMenu.mainMenuStage.show(); // Show main menu
            }
        });

        hint.setOnAction(arg1 -> {
            if (hints > 0) {
                BasicBoard.showHint();    
            }
            hints--;
        });
    }
    private void clearBoardResources() {
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeString = "00:00";
        seconds = 0;
        minutes = 0;
        timer.setText("Timer: " + timeString);
        pane.getChildren().clear();
        bottom.getChildren().clear();
        topVbox.getChildren().clear();
        leftVbox.getChildren().clear();
        rightVbox.getChildren().clear();
    }

    private static String updateTimeString() {
        String secondsString = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        String minutesString = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        finalTime = minutesString + ":" + secondsString;
        return timeString = "Timer: " + minutesString + ":" + secondsString;
    }
}