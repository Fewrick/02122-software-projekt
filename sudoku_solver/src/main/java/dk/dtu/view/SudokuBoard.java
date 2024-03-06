package dk.dtu.view;

import dk.dtu.controller.DFSSolver;
import dk.dtu.controller.SudokuButton;
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
    public Button backtoMenu = new Button("Back to Main Menu");

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

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY / 9);
        bottom.getChildren().addAll(solveSudoku);

        /*HBox topHBox = new HBox(backtoMenu);
        topHBox.setAlignment(Pos.CENTER); // Eller en anden positionering, der passer til din design
        TopVbox.getChildren().add(topHBox);*/

        boardStage.show();

        solveSudoku.setOnAction(arg0 -> {
            try {
                DFSSolver.solveSudoku(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        backtoMenu.setOnAction(arg0 -> {
            boardStage.close();
            App.mainMenuStage.show();
        });
    }

}
