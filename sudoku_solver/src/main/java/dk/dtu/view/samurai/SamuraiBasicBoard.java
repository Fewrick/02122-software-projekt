package dk.dtu.view.samurai;

import dk.dtu.controller.SudokuButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SamuraiBasicBoard {

    public static void createSamuraiSudoku(Pane mainPane) {
        //mainPane.getChildren().clear(); // Ryd eksisterende indhold for en ren start
    
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
        SudokuButton[][] buttons = new SudokuButton[9][9]; // Opret et array til at holde knapperne
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuButton btn = new SudokuButton(0);
                btn.setPrefSize(30, 30);
                buttons[i][j] = btn; // Gem knappen i arrayet
                gridPane.add(btn, j, i);
            }
        }
        
        // Anvend sorte kanter på relevante knapper
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                blackBorder(buttons, i, j); // Kalder blackBorder her
            }
        }
        
        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
        mainPane.getChildren().add(gridPane);
    }
    
    // Husk at opdatere `gridSize` til at være en statisk variabel eller direkte inkluderet i `blackBorder` metoden, hvis den ikke allerede er det.
    

    private static int gridSize = 9;

    private static void blackBorder(SudokuButton[][] buttons, int row, int column) {
        SudokuButton button = buttons[row][column];

        // Add black borders to separate 3x3 boxes
        if ((column + 1) % 3 == 0 && column + 1 != gridSize) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
        }
        if ((row + 1) % 3 == 0 && row + 1 != gridSize) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
        }
        if ((column + 1) % 3 == 0 && column != gridSize - 1 && (row + 1) % 3 == 0 && row != gridSize - 1) {
            button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");
        }
    }
    
}
