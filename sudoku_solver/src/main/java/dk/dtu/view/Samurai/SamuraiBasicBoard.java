package dk.dtu.view.Samurai;

import dk.dtu.controller.SudokuButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SamuraiBasicBoard {
    // Antag at størrelsen er justeret til at rumme Samurai Sudoku layoutet
    //static SudokuButton[][][] buttons2D = new SudokuButton[5][9][9]; // Array til fem 9x9 bræt

    public static void createSamuraiSudoku(Pane mainPane) {
        mainPane.getChildren().clear(); // Ryd eksisterende indhold for en ren start
    
        // Antagelser for størrelse og position
        int gridSize = 9;
        int cellSize = 30; 
        int boardSize = gridSize * cellSize; 
        int overlap = cellSize * 3; 
    
        // Centrale grids position beregnet til at være i midten af mainPane
        double centerX = (mainPane.getPrefWidth() / 2) - (boardSize / 2);
        double centerY = (mainPane.getPrefHeight() / 2) - (boardSize / 2);
    
        // Opret og arranger det centrale grid centralt
        createAndPlaceGrid(mainPane, centerX, centerY);
    
        // Top-left grid 
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap, centerY - boardSize + overlap);
        
        // Top-right grid 
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap, centerY - boardSize + overlap);
    
        // Bottom-left grid 
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap, centerY + boardSize - overlap);
    
        // Bottom-right grid
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap, centerY + boardSize - overlap);
    
        mainPane.requestLayout(); 
    }
    
    
    private static void createAndPlaceGrid(Pane mainPane, double x, double y) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuButton btn = new SudokuButton(0);
                btn.setPrefSize(30, 30);
                gridPane.add(btn, j, i);
            }
        }
        
        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
        mainPane.getChildren().add(gridPane);
    }
    
}
