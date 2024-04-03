package dk.dtu.view.easy;

import dk.dtu.controller.SudokuButton;
import javafx.scene.layout.GridPane;

public class BasicBoard4x4 {
    static int sizeX = 400; // Justeret for et mindre vindue
    static int sizeY = 400;
    static int gridSize = 4; // 4x4 grid
    static int btnSize = sizeX / gridSize; // Størrelse af hver knap
    static int lastClickedRow = -1;
    static int lastClickedColumn = -1;
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    public static boolean displayNum(int row, int column) {
        // Antager at du har en tilsvarende lagringsmekanisme for boardets tilstand
        // Returnerer true hvis der skal vises et nummer, ellers false
        return Board4x4.grid[row][column] != 0;
    }

    public static void createSudoku(GridPane pane) {
        pane.getChildren().clear(); // Ryd panelet for eksisterende komponenter

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                String buttonText = displayNum(row, column) ? String.valueOf(Board4x4.grid[row][column]) : "";
                SudokuButton button = new SudokuButton(0); // Antager en tilsvarende konstruktør eksisterer
                button.setPrefSize(btnSize, btnSize);

                button.setText(buttonText);
                button.setStyle("-fx-text-fill: black; -fx-font-size: 2em; -fx-font-weight: bold;");
                // Tilføj sorte kanter for at adskille 2x2 bokse
                if ((column + 1) % 2 == 0 && column + 1 != gridSize) {
                    button.setStyle(button.getStyle() + "; -fx-border-width: 0 2 0 0; -fx-border-color: lightgrey black lightgrey lightgrey;");
                }
                if ((row + 1) % 2 == 0 && row + 1 != gridSize) {
                    button.setStyle(button.getStyle() + "; -fx-border-width: 0 0 2 0; -fx-border-color: lightgrey lightgrey black lightgrey;");
                }
                if ((column + 1) % 2 == 0 && column != gridSize - 1 && (row + 1) % 2 == 0 && row != gridSize - 1) {
                    button.setStyle(button.getStyle() + "; -fx-border-width: 0 2 2 0; -fx-border-color: lightgrey black black lightgrey;");
                }
                
    
                buttons2D[row][column] = button;

                buttons2D[row][column] = button;

                // Tilføj event handler for klik på knappen
                int finalRow = row;
                int finalColumn = column;
                button.setOnAction(event -> clickedButton(finalRow, finalColumn));

                pane.add(button, column, row);
            }
        }
    }

    private static void clickedButton(int row, int column) {
        // Implementer logik for når en knap klikkes
    }

}
