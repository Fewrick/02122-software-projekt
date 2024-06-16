package dk.dtu.view.samurai;

import dk.dtu.controller.SudokuButton;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SamuraiBasicBoard {

    public static void createSamuraiSudoku(Pane mainPane, int[][][] samuraiData) {
        // Antagelser for størrelse og position
        int gridSize = 9;
        int cellSize = 30; 
        int boardSize = gridSize * cellSize; 
        int overlap = cellSize * 3;

        double centerX = (mainPane.getPrefWidth() / 2) - (boardSize / 2);
        double centerY = (mainPane.getPrefHeight() / 2) - (boardSize / 2);

        // Creating grids for Samurai Sudoku
        createAndPlaceGrid(mainPane, centerX, centerY, samuraiData[0]);
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap, centerY - boardSize + overlap, samuraiData[1]);
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap, centerY - boardSize + overlap, samuraiData[2]);
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap, centerY + boardSize - overlap, samuraiData[3]);
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap, centerY + boardSize - overlap, samuraiData[4]);
    }

    private static void createAndPlaceGrid(Pane mainPane, double x, double y, int[][] data) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(280, 280);
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuButton btn = new SudokuButton(0);
                btn.setPrefSize(30, 30);
                btn.setStyle("-fx-background-radius: 0");
                btn.setText(data[i][j] == 0 ? "" : Integer.toString(data[i][j]));
                int columnIndex = j + (j / 3);
                int rowIndex = i + (i / 3);
                gridPane.add(btn, columnIndex, rowIndex);
            }
        }

        gridPane.setPadding(new Insets(3)); // Adding a black border
        mainPane.getChildren().add(gridPane);
        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
    }
}
