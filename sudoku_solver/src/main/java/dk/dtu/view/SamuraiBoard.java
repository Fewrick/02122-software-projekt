package dk.dtu.view;

import javafx.util.Duration;
import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.SudokuButton;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SamuraiBoard extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 1200; // Standard størrelse for Samurai Sudoku
    static int sizeY = 900; // Standard størrelse for Samurai Sudoku
    public static int gridSize = 21; // Standard grid størrelse for Samurai Sudoku
    static int cellSize = 30; // Størrelsen af hver celle i Samurai Sudoku
    public static int mistakes = 0;
    public static Boolean lifeOn = true;
    public static boolean unique = false;

    static Button solveSudoku = new Button("Solution");
    public Button backtoMenu = new Button("Back to Menu");
    static Button hint = new Button("Hint");
    public static Button lifeButton = new Button("Mistakes: " + mistakes + "/3");
    public static Button timer = new Button(updateTimeString());

    static SudokuButton[][] buttons2D;

    // Application layout
    public static BorderPane borderPane;
    public static GridPane pane = new GridPane();
    public static VBox leftVbox = new VBox();
    public static VBox rightVbox = new VBox();
    public static HBox topVbox = new HBox();
    public static HBox bottom = new HBox();

    // Timer variabler
    public static int seconds = 0;
    public static int minutes = 0;
    public static String timeString = "00:00";
    public static String finalTime = "00:00";
    public static Timeline timeline;

    public SamuraiBoard(int boardSize) {
        if (boardSize == 3) {
            // Samurai Sudoku specifikationer
            sizeX = 1200; // Større vindue for Samurai Sudoku
            sizeY = 900;
            gridSize = 21; // Samurai Sudoku grid størrelse
        } else {
            // Standard Sudoku
            gridSize = boardSize;
        }
        cellSize = sizeX / gridSize;
        buttons2D = new SudokuButton[gridSize][gridSize];
    }

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();

        boardStage = stage;
        boardStage.setTitle(gridSize == 21 ? "Samurai Sudoku Game" : "Sudoku Game");

        Pane centerPane = new Pane();
        centerPane.setPrefSize(sizeX, sizeY);
        centerPane.getChildren().add(pane);
        pane.setLayoutX((sizeX - gridSize * cellSize) * 100);
        pane.setLayoutY((sizeY - gridSize * cellSize) / 6);

        borderPane.setBottom(bottom);
        borderPane.setCenter(centerPane);
        borderPane.setLeft(leftVbox);
        borderPane.setRight(rightVbox);
        borderPane.setTop(topVbox);

        // Opret Sudoku bræt
        System.out.println("Generating sudoku board...");
        if (gridSize == 21) {
            // Opret Samurai Sudoku
            BasicBoard.createSamuraiSudoku(pane);
        } else {
            // Opret standard Sudoku
            BasicBoard.createSudoku(pane, gridSize, unique);
        }
        System.out.println("Sudoku board generated");

        // Opsætning af pane layout
        topVbox.setPrefHeight(sizeY / gridSize - 20);
        leftVbox.setPrefWidth(sizeX / gridSize - 200);
        rightVbox.setPrefWidth(sizeX / gridSize - 200);

        String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
                + "-fx-font-size: 1.3em; -fx-min-width: 130px; -fx-min-height: 40px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        solveSudoku.setStyle(buttonStyle);
        backtoMenu.setStyle(buttonStyle);
        hint.setStyle(buttonStyle);
        timer.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em;");

        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Forstør knapper ved hover
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

        // Livssystem opsætning
        if (lifeOn) {
            HBox.setMargin(lifeButton, new javafx.geometry.Insets(40, 0, 0, 370));
            topVbox.getChildren().add(lifeButton);
            lifeButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em; ");
        }

        // Timer opsætning
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

        // Start timer
        timeline.play();

        boardStage.show();

        // Knap handlinger
        solveSudoku.setOnAction(arg0 -> {
            try {
                if (gridSize == 21) {
                    BasicBoard.showSolution(pane); // Tilpas dette hvis Samurai Sudoku løsning skal vises anderledes
                } else {
                    BasicBoard.showSolution(pane);
                }
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
            mistakes = 0;
            MainMenu.mainMenuStage.show();
        });

        hint.setOnAction(arg1 -> {
            BasicBoard.showHint();
        });
    }

    private static String updateTimeString() {
        String secondsString = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        String minutesString = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        finalTime = minutesString + ":" + secondsString;
        return timeString = "Timer: " + minutesString + ":" + secondsString;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
