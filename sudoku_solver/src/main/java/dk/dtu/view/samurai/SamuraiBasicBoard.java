package dk.dtu.view.samurai;

import dk.dtu.controller.PuzzleGenerator;
import dk.dtu.controller.SudokuButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SamuraiBasicBoard {

    private static int gridSize = 9;

    public static void createSamuraiSudoku(Pane mainPane) {

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
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap - 5.5, centerY - boardSize + overlap - 5.3);

        // Top-right grid
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap + 4.5, centerY - boardSize + overlap - 5.3);

        // Bottom-left grid
        createAndPlaceGrid(mainPane, centerX - boardSize + overlap - 5.5, centerY + boardSize - overlap + 5.3);

        // Bottom-right grid
        createAndPlaceGrid(mainPane, centerX + boardSize - overlap + 4.5, centerY + boardSize - overlap + 4.5);

        mainPane.requestLayout();
    }

    private static void createAndPlaceGrid(Pane mainPane, double x, double y) {
        Pane outerPane = new Pane();
        outerPane.setStyle("-fx-background-color: black;");

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(280, 280);
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                SudokuButton btn = new SudokuButton(0);
                btn.setPrefSize(30, 30);
                btn.setStyle("fx-background-radius: 0");

                int columnIndex = j + (j / 3);
                int rowIndex = i + (i / 3);

                int[][][] puzzle = PuzzleGenerator.generateSamuraiSudoku();
                //set the nunmbers on the puzzle to the btns
                btn.setText(String.valueOf(puzzle[0][i][j]));
                if (puzzle[0][i][j] == 0) {
                    btn.setText("");
                    btn.isEditable();
                }

                // Add black borders to separate 3x3 boxes
                addBlackBorder(btn, i, j, gridSize);

                gridPane.add(btn, columnIndex, rowIndex);
            }
        }

        // Tilføjer en sort ramme rundt om GridPane
        gridPane.setPadding(new Insets(5)); // Bredden på rammen

        outerPane.getChildren().add(gridPane);
        outerPane.setLayoutX(x);
        outerPane.setLayoutY(y);
        mainPane.getChildren().add(outerPane);
    }

    private static void addBlackBorder(Button btn, int i, int j, int gridSize) {
        if ((j + 1) % Math.sqrt(gridSize) == 0 && j + 1 != gridSize) {
            btn.setStyle("-fx-border-color: black; -fx-border-width: 0 2px 0 0;");
        }
        if ((i + 1) % Math.sqrt(gridSize) == 0 && i + 1 != gridSize) {
            btn.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2px 0;");
        }
        if ((j + 1) % Math.sqrt(gridSize) == 0 && j != gridSize - 1 && (i + 1) % Math.sqrt(gridSize) == 0
                && i != gridSize - 1) {
            btn.setStyle(btn.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 2px 2px 0;");
        }
    }
}