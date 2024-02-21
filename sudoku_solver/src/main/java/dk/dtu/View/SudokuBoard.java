package dk.dtu.View;

import dk.dtu.controller.Solver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SudokuBoard extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 810;
    static int sizeY = 810;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static Button solveSudoku = new Button("Solve!!");

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    BorderPane borderPane = new BorderPane();
    GridPane pane = new GridPane();
    public static HBox bottom = new HBox();

    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        BasicBoard.basicSudoku(pane);

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        // Constructs pane
        pane.setPrefSize(sizeX, sizeY);
        pane.setStyle("-fx-background-color: #5DADE2;"); // Sets background color: Green

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY / 9);
        bottom.getChildren().addAll(solveSudoku);

        boardStage.show();

        solveSudoku.setOnAction(arg0 -> {
            try {
                solveSudoku(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void solveSudoku(ActionEvent event) throws Exception {
        if (Solver.solveSudoku(Grid.board)) {
            BasicBoard.basicSudoku(pane);
        } else {
            System.out.println("Could not compute");
        }
    }
}
