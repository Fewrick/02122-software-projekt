package dk.dtu.view;

import javafx.scene.layout.GridPane;

public class BasicBoard {
    static int sizeX = 810;
    static int sizeY = 810;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];
    
    public static void basicSudoku(GridPane pane){
        for (int row = 0; row < gridSize; row++) {
			for (int column = 0; column < gridSize; column++) {
				SudokuButton Button = new SudokuButton(0);
				Button.setPrefSize(btnSize, btnSize); // Size of one cell

				pane.add(Button, column, row);
                Button.setText("" + Grid.board[row][column]);
                Button.setStyle("-fx-text-fill: blue; -fx-font-size: 2.0em;");

                // Add black borders to separate 3x3 boxes
            if ((column + 1) % 3 == 0 && column + 1 != gridSize) {
                Button.setStyle(Button.getStyle() + "; -fx-border-color: grey; -fx-border-width: 0 2px 0 0;");
            }
            if ((row + 1) % 3 == 0 && row + 1 != gridSize) {
                Button.setStyle(Button.getStyle() + "; -fx-border-color: grey; -fx-border-width: 0 0 2px 0;");
            }

            if ((column + 1) % 3 == 0 && column != gridSize - 1 && (row + 1) % 3 == 0 && row != gridSize - 1) {
                Button.setStyle(Button.getStyle() + "; -fx-border-color: grey; -fx-border-width: 0 2px 2px 0;");
            }

				buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.


			}
		}
    }
}
