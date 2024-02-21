package dk.dtu.View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

        button1.setText("1");
        button2.setText("2");
        button3.setText("3");
        button4.setText("4");
        button5.setText("5");
        button6.setText("6");
        button7.setText("7");
        button8.setText("8");
        button9.setText("9");

        String buttonStyle = "-fx-text-fill: blue; -fx-font-size: 1.5em; -fx-min-width: 80px; -fx-min-height: 25px;";

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

        bottom.setPrefHeight(sizeY/9);

        boardStage.show();
    }
    
}
