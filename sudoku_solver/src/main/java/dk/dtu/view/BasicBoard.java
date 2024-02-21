package dk.dtu.view;

import dk.dtu.controller.SudokuButton;
import javafx.scene.layout.GridPane;

public class BasicBoard {
    static int sizeX = 810;
    static int sizeY = 810;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    public static boolean displayNum(int row, int column) {
        if (Board.grid[row][column] == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void createSudoku(GridPane pane) {
        String buttonText;
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (displayNum(row, column)) {
                    buttonText = "" + Board.grid[row][column];
                } else {
                    buttonText = "";
                }
                SudokuButton Button = new SudokuButton(0);
                Button.setPrefSize(btnSize, btnSize); // Size of one cell

                pane.add(Button, column, row);
                Button.setText(buttonText);
                Button.setStyle("-fx-text-fill: blue; -fx-font-size: 2.0em;");
                buttons2D[row][column] = Button; // Add coordinates and accessibility to all buttons.
            }
        }
    }
}
