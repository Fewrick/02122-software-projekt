package dk.dtu.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SudokuBoard extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 600;
    static int sizeY = 600;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    BorderPane borderPane = new BorderPane();
    GridPane pane = new GridPane();
    public static HBox bottom = new HBox();

    public void basicSudoku(){
        for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				SudokuButton Button = new SudokuButton(0);
				Button.setPrefSize(btnSize, btnSize); // Size of one cell

				/*// Background
				if (column % 2 == 0 && row % 2 == 0) {
					Button.setStyle("-fx-base: #8B4513");
				} else {
					Button.setStyle("-fx-base: #D2B48C;");
				}
				if (column % 2 != 0 && row % 2 != 0) {
					Button.setStyle("-fx-base: #8B4513");
				}*/

				pane.add(Button, row, column);
				buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.
			}
		}
    }

    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        // Constructs pane
		pane.setStyle("-fx-background-color: #5DADE2;"); // Sets background color: Green

        
        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY/8);

        







        boardStage.show();
    }
    
}
