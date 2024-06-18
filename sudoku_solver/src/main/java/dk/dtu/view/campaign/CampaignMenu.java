package dk.dtu.view.campaign;


import dk.dtu.controller.BasicBoard;
import dk.dtu.view.MainMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CampaignMenu {

    private static final int LEVELS = 30;
    private int currentLevel = 30; // Keep this variable updated as the player progresses
    public Button backtoMenu = new Button("Back to Menu");

    public void showCampaign() {
        Stage campaignStage = new Stage();
        TilePane tilePane = new TilePane();
        tilePane.setHgap(5);
        tilePane.setVgap(5);

        // Load the lock icon from a local file
        Image lockImage = new Image("/images/LockSudoku.png");

        for (int i = 1; i <= LEVELS; i++) {
            final int level = i; // Create a final variable for use in the lambda expression
            Button button = new Button();
            button.setPrefSize(75, 75); // Set the desired size for the button

            if (level > currentLevel) {
                // Create an ImageView with the lock icon and adjust the size to fit the button
                ImageView lockImageView = new ImageView(lockImage);
                lockImageView.setFitWidth(button.getPrefWidth());
                lockImageView.setFitHeight(button.getPrefHeight());
                button.setGraphic(lockImageView);
                button.setDisable(true); // Disable the button
            } else {
                // Set the text on the button and add an event handler
                button.setText("Level " + level);
                button.setOnAction(event -> {
                    try {
                        // Create a new instance of SudokuCampaign
                        SudokuCampaign sudokuBoard = new SudokuCampaign();

                        // Set specific game settings
                        SudokuCampaign.lifeOn = true;
                        SudokuCampaign.mistakes = 0;
                        BasicBoard.difficulty = "level" + level; // Use the final variable here

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

        String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
                + "-fx-font-size: 1.3em; -fx-min-width: 130px; -fx-min-height: 40px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        backtoMenu.setStyle(buttonStyle);

        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        backtoMenu.setOnMouseEntered(e -> backtoMenu.setStyle(buttonStyle + hoverStyle));
        backtoMenu.setOnMouseExited(e -> backtoMenu.setStyle(buttonStyle));

         backtoMenu.setOnAction(arg1 -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(new Stage());
            campaignStage.close();
        });

        StackPane campaignLayout = new StackPane();
        campaignLayout.getChildren().addAll(tilePane, backtoMenu);

        StackPane.setMargin(backtoMenu, new javafx.geometry.Insets(400, 0, 0, 4));

        Scene campaignScene = new Scene(campaignLayout, 600, 600);
        campaignStage.setScene(campaignScene);
        campaignStage.setTitle("Campaign Mode");
        campaignStage.show();
    }
}
