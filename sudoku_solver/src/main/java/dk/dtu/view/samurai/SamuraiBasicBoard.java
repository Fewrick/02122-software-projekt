package dk.dtu.view.samurai;

import dk.dtu.controller.SudokuButton;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SamuraiBasicBoard {
    
    private static final int gridSize = 9;
    private static final int cellSize = 30; // Define cell size here to ensure it is accessible everywhere needed

    public static void createSamuraiSudoku(Pane mainPane, int[][][] samuraiData) {
        int boardSize = gridSize * cellSize; 
        int overlap = cellSize * 3; // Overlap to ensure the grids intersect correctly
    
        // Center grid position calculated to be in the middle of mainPane
        double centerX = (mainPane.getPrefWidth() / 2) - (boardSize / 2);
        double centerY = (mainPane.getPrefHeight() / 2) - (boardSize / 2);
    
        // Create and place the central grid centrally
        createAndPlaceGrid(mainPane, centerX, centerY, samuraiData[0]);
    
        // Top-left grid 
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap, centerY - boardSize + overlap, samuraiData[1]);
        
        // Top-right grid 
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap, centerY - boardSize + overlap, samuraiData[2]);
        
        // Bottom-left grid 
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap, centerY + boardSize - overlap, samuraiData[3]);
    
        // Bottom-right grid
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap, centerY + boardSize - overlap, samuraiData[4]);
    }
    
    private static void createAndPlaceGrid(Pane mainPane, double x, double y, int[][] boardData) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(270, 270); // Slightly smaller to allow for borders and padding
        gridPane.setPadding(new Insets(1)); // Small padding around the grid
        gridPane.setGridLinesVisible(true);
    
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                SudokuButton btn = new SudokuButton(0);
                btn.setPrefSize(cellSize, cellSize);
                btn.setText(boardData[i][j] == 0 ? "" : Integer.toString(boardData[i][j]));
                btn.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");
                gridPane.add(btn, j, i);
            }
        }
        
        // Add border spacing within the grid for visual clarity between 3x3 blocks
        for (int i = 3; i < gridSize; i += 3) {
            for (int j = 0; j < gridSize; j++) {
                Pane verticalSpace = new Pane();
                verticalSpace.setPrefWidth(3);
                gridPane.add(verticalSpace, i, j);

                Pane horizontalSpace = new Pane();
                horizontalSpace.setPrefHeight(3);
                gridPane.add(horizontalSpace, j, i);
            }
        }
    
        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
        mainPane.getChildren().add(gridPane);
    }
}