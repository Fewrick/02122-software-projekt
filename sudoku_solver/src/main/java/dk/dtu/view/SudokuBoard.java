package dk.dtu.view;

import dk.dtu.controller.DFSSolver;
import dk.dtu.controller.SudokuButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
    static Button button1 = new Button();
    static Button button2 = new Button();
    static Button button3 = new Button();
    static Button button4 = new Button();
    static Button button5 = new Button();
    static Button button6 = new Button();
    static Button button7 = new Button();
    static Button button8 = new Button();
    static Button button9 = new Button();

    static Button solveSudoku = new Button("Solve!!");

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    BorderPane borderPane = new BorderPane();
    public static GridPane pane = new GridPane();
    VBox leftVbox = new VBox();
    VBox rightVbox = new VBox();
    VBox TopVbox = new VBox();
    public static HBox bottom = new HBox();

    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        borderPane.setLeft(leftVbox);
        borderPane.setRight(rightVbox);
        borderPane.setTop(TopVbox);
        
        BasicBoard.createSudoku(pane);

        // Constructs pane
        TopVbox.setPrefHeight(sizeY / 9 - 20);
        leftVbox.setPrefWidth(sizeX / 9 - 20);
        rightVbox.setPrefWidth(sizeX / 9 - 20);
        pane.setStyle("-fx-background-color: lightgrey;"); // Sets background color: Green

        button1.setText("1");
        button2.setText("2");
        button3.setText("3");
        button4.setText("4");
        button5.setText("5");
        button6.setText("6");
        button7.setText("7");
        button8.setText("8");
        button9.setText("9");

        String buttonStyle = "-fx-text-fill: darkgrey; -fx-font-size: 1.5em; -fx-min-width: 80px; -fx-min-height: 25px;";

        button1.setStyle(buttonStyle);
        button2.setStyle(buttonStyle);
        button3.setStyle(buttonStyle);
        button4.setStyle(buttonStyle);
        button5.setStyle(buttonStyle);
        button6.setStyle(buttonStyle);
        button7.setStyle(buttonStyle);
        button8.setStyle(buttonStyle);
        button9.setStyle(buttonStyle);

        bottom.getChildren().addAll(button1,button2,button3,button4,button5,button6,button7,button8,button9);
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY / 9);
        bottom.getChildren().addAll(solveSudoku);

        boardStage.show();

        solveSudoku.setOnAction(arg0 -> {
            try {
                DFSSolver.solveSudoku(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
