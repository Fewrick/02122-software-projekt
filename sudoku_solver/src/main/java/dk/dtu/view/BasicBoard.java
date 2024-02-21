package dk.dtu.view;

import dk.dtu.controller.SudokuButton;
import javafx.scene.layout.GridPane;

public class BasicBoard {
    static int sizeX = 810;
    static int sizeY = 810;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];
    
    public static void createSudoku(GridPane pane){
        for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				SudokuButton Button = new SudokuButton(0);
				Button.setPrefSize(btnSize, btnSize); // Size of one cell

				pane.add(Button, column, row);
                Button.setText("" + Board.grid[row][column]);
                Button.setStyle("-fx-text-fill: blue; -fx-font-size: 2.0em;");
				buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.
			}
		}
    }
}
