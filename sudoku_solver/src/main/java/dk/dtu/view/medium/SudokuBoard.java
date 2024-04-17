package dk.dtu.view.medium;

import javafx.util.Duration;
import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.SudokuButton;
import dk.dtu.view.MainMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuBoard extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 800;
    static int sizeY = 800;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    public static int mistakes = 0;
    public static Boolean lifeOn = true;

    static Button solveSudoku = new Button("Solution");
    public Button backtoMenu = new Button("Back to Menu");
    static Button hint = new Button("Hint");
    public static Button lifeButton = new Button("Mistakes: " + mistakes + "/3");
    static Button timer = new Button(updateTimeString());

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    BorderPane borderPane = new BorderPane();
    public static GridPane pane = new GridPane();
    VBox leftVbox = new VBox();
    VBox rightVbox = new VBox();
    public static HBox topVbox = new HBox();
    public static HBox bottom = new HBox();

    // timer variables
    static int seconds = 0;
    static int minutes = 0;
    public static String timeString = "00:00";
    public static String finalTime = "00:00";
    static Timeline timeline;

    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        borderPane.setLeft(leftVbox);
        borderPane.setRight(rightVbox);
        borderPane.setTop(topVbox);

        System.out.println("Generating sudoku board...");
        BasicBoard.createSudoku(pane);
        System.out.println("Sudoku board generated");

        // Constructs pane
        topVbox.setPrefHeight(sizeY / 9 - 20);
        leftVbox.setPrefWidth(sizeX / 9 - 20);
        rightVbox.setPrefWidth(sizeX / 9 - 20);
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
        HBox.setMargin(backtoMenu, new javafx.geometry.Insets(0, 0, 0, 0));
        HBox.setMargin(hint, new javafx.geometry.Insets(0, 150, 0, 150));
        HBox.setMargin(solveSudoku, new javafx.geometry.Insets(0, 0, 0, 0));

        topVbox.setPrefHeight(sizeY / 9);
        topVbox.getChildren().addAll(timer);
        HBox.setMargin(timer, new javafx.geometry.Insets(40, 470, 0, 70));

        // Life options setup
        if (lifeOn) {
            HBox.setMargin(lifeButton, new javafx.geometry.Insets(40, 0, 0, 0));
            topVbox.getChildren().add(lifeButton);
            lifeButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em; ");
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

        solveSudoku.setOnAction(arg0 -> {
            try {
                BasicBoard.showSolution(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        backtoMenu.setOnAction(arg1 -> {
            boardStage.close();
            bottom.getChildren().clear();
            pane.getChildren().clear();
            borderPane.getChildren().clear();
            topVbox.getChildren().clear();
            leftVbox.getChildren().clear();
            rightVbox.getChildren().clear();

            timeline.stop();
            timeline.getKeyFrames().clear();
            timeString = "00:00";
            seconds = 0;
            minutes = 0;
            timer.setText("Timer: " + timeString);
            MainMenu.mainMenuStage.show();
        });

        hint.setOnAction(arg2 -> {
            // make a square figure on the cells with odd numbers
            // make a circle figure on the cells with even numbers
        });
    }

    private static String updateTimeString() {
        String secondsString = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        String minutesString = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        finalTime = minutesString + ":" + secondsString;
        return timeString = "Timer: " + minutesString + ":" + secondsString;
    }

    public static void evenOdd(int row, int column, int[][] board) {
        // Load the circle image
        Image circleImage = new Image("path_to_your_circle_image.png");

        for (row = 0; row < gridSize; row++) {
            if (BasicBoard.puzzleBoard[row][column] % 2 == 0) {
                ImageView circleImageView = new ImageView(circleImage);
                circleImageView.setFitWidth(btnSize);
                circleImageView.setFitHeight(btnSize);
                pane.getChildren().add(circleImageView);
            }
        }
    }
}
