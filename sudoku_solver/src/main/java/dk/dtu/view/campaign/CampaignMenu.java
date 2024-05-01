package dk.dtu.view.campaign;

import dk.dtu.controller.BasicBoard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CampaignMenu {

    private static final int LEVELS = 30;
    private int currentLevel = 1; // Keep this variable updated as the player progresses

    public void showCampaign() {
        Stage campaignStage = new Stage();
        TilePane tilePane = new TilePane();
        tilePane.setHgap(5);
        tilePane.setVgap(5);

        // Load the lock icon from a local file
        Image lockImage = new Image("dk/dtu/view/image/LockSudoku.png");

        for (int i = 1; i <= LEVELS; i++) {
            Button button = new Button();
            button.setPrefSize(75, 75); // Set the desired size for the button

            if (i > currentLevel) {
                // Create an ImageView with the lock icon and adjust the size to fit the button
                ImageView lockImageView = new ImageView(lockImage);
                lockImageView.setFitWidth(button.getPrefWidth());
                lockImageView.setFitHeight(button.getPrefHeight());
                button.setGraphic(lockImageView);
                button.setDisable(true); // Disable the button
            } else {
                // Set the text on the button and add an event handler
                button.setText("Level " + i);
                button.setOnAction(event -> {
                    try {
                        // Create a new instance of SudokuBoard with Medium settings
                        SudokuCampaign sudokuBoard = new SudokuCampaign();

                        // Set specific game settings
                        SudokuCampaign.lifeOn = true;
                        SudokuCampaign.mistakes = 0;
                        BasicBoard.difficulty = "Medium";

                        Stage sudokuStage = new Stage(); 
                        sudokuBoard.start(sudokuStage);

                        // Close the campaign window
                        campaignStage.close(); 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            
            tilePane.getChildren().add(button);
        }

        StackPane campaignLayout = new StackPane();
        campaignLayout.getChildren().add(tilePane);

        Scene campaignScene = new Scene(campaignLayout, 600, 600);
        campaignStage.setScene(campaignScene);
        campaignStage.setTitle("Campaign Mode");
        campaignStage.show();
    }
}







//"dk/dtu/view/image/SudokuLock.png"