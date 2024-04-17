package dk.dtu.view.samurai;

import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.SudokuButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SamuraiBasicBoard {
    
    private static int gridSize = 9;

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
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap-5.5, centerY - boardSize + overlap-5.3);
        
        // Top-right grid 
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap+4.5, centerY - boardSize + overlap-5.3);
        
        // Bottom-left grid 
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap-5.5, centerY + boardSize - overlap+5.3);
    
        // Bottom-right grid
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap+4.5, centerY + boardSize - overlap+4.5);
    
        mainPane.requestLayout(); 
    }
    
    
    private static void createAndPlaceGrid(Pane mainPane, double x, double y) {
        Pane outerPane = new Pane();
        outerPane.setStyle("-fx-background-color: black;");

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(280, 280);
        gridPane.setGridLinesVisible(true);
    
        // Tallet repræsenterer værdien i hvert felt på brættet
        int[][] sudokuValues = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int value = sudokuValues[i][j];
                SudokuButton btn = new SudokuButton(value); // Opretter knap med det angivne tal
                btn.setPrefSize(30, 30);
                btn.setStyle("-fx-background-radius: 0");
    
                int columnIndex = j + (j / 3);
                int rowIndex = i + (i / 3);
                gridPane.add(btn, columnIndex, rowIndex);
    
                // Tilføj værdien ovenpå knappen
                if (value != 0) {
                    Button valueButton = new Button(String.valueOf(value));
                    valueButton.setPrefSize(30, 30);
                    valueButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
                    gridPane.add(valueButton, columnIndex, rowIndex);
                }
            }
        }
    
        // Tilføjer en sort ramme rundt om GridPane
        gridPane.setPadding(new Insets(3)); // Bredden på rammen
    
        // Tilføjer tomme Pane-objekter som "borders" mellem 3x3 blokke
        for (int i = 3; i < gridSize + 2; i += 4) {
            for (int j = 0; j < gridSize + 2; j++) {
                Pane verticalSpace = new Pane();
                verticalSpace.setPrefWidth(3);
                gridPane.add(verticalSpace, i, j);
    
                Pane horizontalSpace = new Pane();
                horizontalSpace.setPrefHeight(3);
                gridPane.add(horizontalSpace, j, i);
            }
        }
    
        outerPane.getChildren().add(gridPane);
        outerPane.setLayoutX(x);
        outerPane.setLayoutY(y);
        mainPane.getChildren().add(outerPane);
    }
}    