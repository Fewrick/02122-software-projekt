package dk.dtu.view.campaign;

import dk.dtu.controller.BasicBoard;
import dk.dtu.view.medium.SudokuBoard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CampaignMenu {

    private static final int LEVELS = 30;
    private int currentLevel; // Denne værdi initialiseres fra en fil

    public CampaignMenu() {
        this.currentLevel = readCurrentLevel(); // Læs den aktuelle niveauværdi ved opstart
    }

    public void showCampaign() {
        Stage campaignStage = new Stage();
        VBox layout = new VBox(10);
        TilePane tilePane = createLevelButtons(campaignStage);
        Button resetButton = createResetButton(campaignStage);

        layout.getChildren().addAll(tilePane, resetButton);

        Scene campaignScene = new Scene(layout, 600, 650);
        campaignStage.setScene(campaignScene);
        campaignStage.setTitle("Campaign Mode");
        campaignStage.show();
    }

    private TilePane createLevelButtons(Stage campaignStage) {
        TilePane tilePane = new TilePane();
        tilePane.setHgap(5);
        tilePane.setVgap(5);

        Image lockImage = new Image("dk/dtu/view/image/LockSudoku.png");

        for (int i = 1; i <= LEVELS; i++) {
            final int level = i;
            Button button = new Button();
            button.setPrefSize(75, 75);

            if (level > currentLevel) {
                ImageView lockImageView = new ImageView(lockImage);
                lockImageView.setFitWidth(button.getPrefWidth());
                lockImageView.setFitHeight(button.getPrefHeight());
                button.setGraphic(lockImageView);
                button.setDisable(true);
            } else {
                button.setText("Level " + level);
                button.setOnAction(event -> playLevel(campaignStage, level));
            }
            tilePane.getChildren().add(button);
        }
        return tilePane;
    }

    private Button createResetButton(Stage campaignStage) {
        Button resetButton = new Button("Reset Progress");
        resetButton.setOnAction(event -> {
            resetProgress();
            campaignStage.getScene().setRoot(new VBox(10, createLevelButtons(campaignStage), createResetButton(campaignStage)));
        });
        return resetButton;
    }

    private void playLevel(Stage campaignStage, int level) {
        // Set the return context to campaignMenu
        //gameCompleted = true;
        
        SudokuBoard.returnContext = "campaignMenu";
        BasicBoard.difficulty = "level" + level;
    
        // Create and show the Sudoku board
        SudokuBoard sudokuBoard = new SudokuBoard(3);  // Assuming a no-arg constructor is available
        Stage sudokuStage = new Stage();
        if (level == currentLevel) {
            currentLevel++;
            updateCurrentLevel(); // Opdater filen med den nye niveauværdi
            campaignStage.getScene().setRoot(createLevelButtons(campaignStage));
        }
        try {
            sudokuBoard.start(sudokuStage);  // Start the Sudoku board
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Close the campaign stage
        campaignStage.close();
    }

    private int readCurrentLevel() {
        try {
            String levelString = new String(Files.readAllBytes(Paths.get("levelProgress.txt")));
            return Integer.parseInt(levelString.trim());
        } catch (IOException | NumberFormatException e) {
            return 1;
        }
    }

    private void updateCurrentLevel() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("levelProgress.txt"))) {
            writer.write(String.valueOf(currentLevel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetProgress() {
        currentLevel = 1;
        updateCurrentLevel();
    }
}