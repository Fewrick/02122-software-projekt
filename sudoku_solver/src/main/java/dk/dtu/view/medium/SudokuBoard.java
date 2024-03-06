package dk.dtu.view.medium;

import dk.dtu.controller.DFSSolver;
import dk.dtu.controller.SudokuButton;
import dk.dtu.view.MainMenu;
import dk.dtu.view.BasicBoard;
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

    public static Stage boardStage = new Stage();
    static int sizeX = 800;
    static int sizeY = 800;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static int mistakes = 0;

    static Button solveSudoku = new Button("Solution");
    public Button backtoMenu = new Button("Back to Menu");
    static Button hint = new Button("Hint");
    static Button lifeButton = new Button("Mistakes: " + mistakes + "/3");
    static Button timer = new Button("Timer: 00:00");

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    BorderPane borderPane = new BorderPane();
    public static GridPane pane = new GridPane();
    VBox leftVbox = new VBox();
    VBox rightVbox = new VBox();
    public static HBox topVbox = new HBox();
    public static HBox bottom = new HBox();

    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        borderPane.setLeft(leftVbox);
        borderPane.setRight(rightVbox);
        borderPane.setTop(topVbox);

        BasicBoard.createSudoku(pane);

        // Constructs pane
        topVbox.setPrefHeight(sizeY / 9 - 20);
        leftVbox.setPrefWidth(sizeX / 9 - 20);
        rightVbox.setPrefWidth(sizeX / 9 - 20);
        pane.setStyle("-fx-background-color: lightgrey;");

        String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
                + "-fx-font-size: 1.3em; -fx-min-width: 130px; -fx-min-height: 40px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
     //   solveSudoku.setStyle(buttonStyle);
        backtoMenu.setStyle(buttonStyle);
        hint.setStyle(buttonStyle);
        lifeButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em; ");
        timer.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black; -fx-font-size: 1.1em;");

        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        // solveSudoku.setOnMouseEntered(e -> solveSudoku.setStyle(buttonStyle + hoverStyle));
        // solveSudoku.setOnMouseExited(e -> solveSudoku.setStyle(buttonStyle));
        backtoMenu.setOnMouseEntered(e -> backtoMenu.setStyle(buttonStyle + hoverStyle));
        backtoMenu.setOnMouseExited(e -> backtoMenu.setStyle(buttonStyle));
        hint.setOnMouseEntered(e -> hint.setStyle(buttonStyle + hoverStyle));
        hint.setOnMouseExited(e -> hint.setStyle(buttonStyle));

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY / 9);
        bottom.getChildren().addAll(backtoMenu, hint/* , solveSudoku*/);
        bottom.setAlignment(Pos.CENTER);
        HBox.setMargin(backtoMenu, new javafx.geometry.Insets(0, 0, 0, 0));
        HBox.setMargin(hint, new javafx.geometry.Insets(0, 150, 0, 150));
        HBox.setMargin(solveSudoku, new javafx.geometry.Insets(0, 0, 0, 0));

        topVbox.setPrefHeight(sizeY / 9);
        topVbox.getChildren().addAll(lifeButton, timer);
        HBox.setMargin(lifeButton, new javafx.geometry.Insets(40, 0, 0, 65));
        HBox.setMargin(timer, new javafx.geometry.Insets(40, 0, 0, 480));



        boardStage.show();

        // solveSudoku.setOnAction(arg0 -> {
        //     try {
        //         DFSSolver.solveSudoku(arg0);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // });
        backtoMenu.setOnAction(arg0 -> {
            boardStage.close();
            bottom.getChildren().clear();
            pane.getChildren().clear();
            borderPane.getChildren().clear();
            topVbox.getChildren().clear();
            leftVbox.getChildren().clear();
            rightVbox.getChildren().clear();
            MainMenu.mainMenuStage.show();
        });
    }

}
