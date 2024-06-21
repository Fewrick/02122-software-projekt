package dk.dtu.view.campaign;

import dk.dtu.controller.BasicBoard;
import dk.dtu.view.MainMenu;
import dk.dtu.view.SudokuBoard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CampaignMenu {

    private static final int LEVELS = 30;
    public static boolean isDone;
    public static int currentLevel; // Denne værdi initialiseres fra en fil

    /**
     * This method initializes a CampaignMenu object.
     * It reads the value of 'isDone' and 'currentLevel' fields from files
     * and assigns them to the corresponding fields in the CampaignMenu object.
     */
    public CampaignMenu() {
        isDone = readDone();
        currentLevel = readCurrentLevel(); // Læs den aktuelle niveauværdi ved opstart
    }

    private VBox initializeLayout(Stage campaignStage){
        campaignStage.setResizable(false);
        VBox layout = new VBox(10);
        Text text = new Text();
        text.setFont(new Font("Arial", 20));
        text.setText("                         Welcome to campaign mode! \n                     Complete a level to unlock the next");
        TilePane tilePane = createLevelButtons(campaignStage);
        Button resetButton = createResetButton(campaignStage);
        Button backToMenu = new Button("Back to Main Menu");

        String buttonStyle1 = "-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; " +
                "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;";

        resetButton.setStyle(buttonStyle1);
        resetButton.setOnMouseEntered(e -> resetButton.setStyle(buttonStyle1 + hoverStyle));
        resetButton.setOnMouseExited(e -> resetButton.setStyle(buttonStyle1));

        backToMenu.setStyle(buttonStyle1);
        backToMenu.setOnMouseEntered(e -> backToMenu.setStyle(buttonStyle1 + hoverStyle));
        backToMenu.setOnMouseExited(e -> backToMenu.setStyle(buttonStyle1));

        // Button behavior
        backToMenu.setOnAction(arg0 -> {
            campaignStage.close();
            MainMenu.mainMenuStage.show();
        });

        VBox.setMargin(resetButton, new javafx.geometry.Insets(30, 0, 0, 220));
        VBox.setMargin(backToMenu, new javafx.geometry.Insets(10, 0, 0, 200));
        layout.getChildren().addAll(text,tilePane, resetButton, backToMenu);

        return layout;
    }

    /**
     * This method shows the Campaign Mode menu by creating a new Stage and setting its scene.
     * The menu contains a TilePane with level buttons and a reset button.
     *
     * @return void
     */
    public void showCampaign() {
        Stage campaignStage = new Stage();
        VBox layout = initializeLayout(campaignStage);

        Scene campaignScene = new Scene(layout, 600, 650);
        campaignStage.setScene(campaignScene);
        campaignStage.setTitle("Campaign Mode");
        campaignStage.show();
    }

    /**
     * Creates a TilePane with level buttons for the Campaign Mode menu.
     *
     * @param campaignStage the Stage for the Campaign Mode menu
     * @return the TilePane with level buttons
     */
    private TilePane createLevelButtons(Stage campaignStage) {
        TilePane tilePane = new TilePane();
        tilePane.setHgap(5);
        tilePane.setVgap(5);

        Image lockImage = new Image("/images/LockSudoku.png");

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
            campaignStage.getScene().setRoot(initializeLayout(campaignStage));
        });
        return resetButton;
    }

    /**
     * Plays a specific level in the campaign mode.
     *
     * @param campaignStage the stage of the campaign menu
     * @param level the level to be played
     */
    private void playLevel(Stage campaignStage, int level) {
        System.out.println("Started game on level " + level);

        // Set the return context to campaignMenu
        //gameCompleted = true;
        
        SudokuBoard.returnContext = "campaignMenu";
        BasicBoard.difficulty = "level" + level;
    
        // Create and show the Sudoku board
        SudokuBoard sudokuBoard = new SudokuBoard(3);  // Assuming a no-arg constructor is available
        Stage sudokuStage = new Stage();

        if (level == currentLevel) {
            System.out.println("level " + level);
            System.out.println("current level " + currentLevel);
            if (isDone) {
                updateIsDone(false);
                campaignStage.getScene().setRoot(createLevelButtons(campaignStage));
            }
        }
        try {
            sudokuBoard.start(sudokuStage);  // Start the Sudoku board
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Close the campaign stage
        campaignStage.close();
    }

    private boolean readDone() {
        try {
            String levelString = new String(Files.readAllBytes(Paths.get("levelIsDone.txt")));
            System.out.println("value of level is " + levelString);
            return levelString.equals("t");
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }

    public static void updateIsDone(boolean value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("levelIsDone.txt"))) {
            if (value) {
                writer.write("t");
            } else {
                writer.write("f");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int readCurrentLevel() {
        try {
            String levelString = new String(Files.readAllBytes(Paths.get("levelProgress.txt")));
            return Integer.parseInt(levelString.trim());
        } catch (IOException | NumberFormatException e) {
            return 1;
        }
    }

    public static void updateCurrentLevel() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("levelProgress.txt"))) {
            writer.write(String.valueOf(currentLevel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetProgress() {
        currentLevel = 1;
        updateCurrentLevel();
        updateIsDone(false);
    }
}