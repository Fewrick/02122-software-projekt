package dk.dtu.view;

import dk.dtu.controller.BasicBoard;
import dk.dtu.view.campaign.CampaignMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameSettingsMenu {

    private final int sizeX = 700;
    private final int sizeY = 700;
    public Button classicBtn = new Button("Classic");
    public Button samuraiBtn = new Button("Samurai");
    public Button easyBtn = new Button("Easy");
    public Button mediumBtn = new Button("Medium");
    public Button hardBtn = new Button("Hard");
    public Button CampaignBtn = new Button("Campaign");

    public Button customSizeBtn = new Button("Custom");
    public Button submitSizeBtn = new Button("Start game");
    public String size;
    public CheckBox lifeCheckBox = new CheckBox("Life");
    public CheckBox uniqueCheckBox = new CheckBox("Uniqueness");

    /**
     * Opens a stage for game settings.
     * The stage displays different options for selecting game settings, such as
     * difficulty level and game mode.
     */
    public void GameSettings() {

        Stage settingStage = new Stage();
        StageStyle style = StageStyle.DECORATED;
        settingStage.initStyle(style);
        settingStage.setTitle("Game Settings");
        settingStage.show();

        settingStage.setWidth(sizeX);
        settingStage.setHeight(sizeY);

        ImageView EasyView = new ImageView(new Image("/images/easyboard.png"));
        EasyView.setFitWidth(400); // Sæt ønsket bredde
        EasyView.setFitHeight(400); // Sæt ønsket højde
        EasyView.setPreserveRatio(true);
        EasyView.setVisible(false);

        ImageView MediumView = new ImageView(new Image("/images/mediumboard.png"));
        MediumView.setFitWidth(400); // Sæt ønsket bredde
        MediumView.setFitHeight(400); // Sæt ønsket højde
        MediumView.setPreserveRatio(true);
        MediumView.setVisible(false);

        ImageView HardView = new ImageView(new Image("/images/Hard.png"));
        HardView.setFitWidth(400); // Sæt ønsket bredde
        HardView.setFitHeight(400); // Sæt ønsket højde
        HardView.setPreserveRatio(true);
        HardView.setVisible(false);

        ImageView SamuraiView = new ImageView(new Image("/images/Samurai-sudoku.png"));
        SamuraiView.setFitWidth(400); // Sæt ønsket bredde
        SamuraiView.setFitHeight(400); // Sæt ønsket højde
        SamuraiView.setPreserveRatio(true);
        SamuraiView.setVisible(false);

        ImageView imageView = new ImageView(new Image("/images/Classic.png"));
        imageView.setFitWidth(400); // Sæt ønsket bredde
        imageView.setFitHeight(400); // Sæt ønsket højde
        imageView.setPreserveRatio(true);
        imageView.setVisible(false);

        ImageView CampaignView = new ImageView(new Image("/images/Campaign.png"));
        CampaignView.setFitWidth(400); // Sæt ønsket bredde
        CampaignView.setFitHeight(400); // Sæt ønsket højde
        CampaignView.setPreserveRatio(true);
        CampaignView.setVisible(false);

        Label descriptionLabel = new Label(
                "Classic Sudoku \nLives: OFF \nHints: OFF \nCells removed: ~40");
        descriptionLabel.setVisible(false);
        Label classicDescLabel = new Label(
                "Classic Sudoku \nLives: OFF \nHints: OFF \nCells removed: ~40");
        classicDescLabel.setVisible(false);

        Label samuraiDescLabel = new Label("Samurai Sudoku");
        samuraiDescLabel.setVisible(false);

        Label easyDescLabel = new Label("Easy Sudoku \nLives: 3 \nHints: 3 \nCells removed: ~24");
        easyDescLabel.setVisible(false);

        Label mediumDescLabel = new Label("Medium Sudoku \nLives: 3 \nHints: 3 \nCells removed: ~40");
        mediumDescLabel.setVisible(false);

        Label hardDescLabel = new Label("Hard Sudoku \nLives: 3 \nHints: 3 \nCells removed: ~55");
        hardDescLabel.setVisible(false);

        Label campaignDescLabel = new Label("Campaign Sudoku");
        campaignDescLabel.setVisible(false);

        Label customDescLabel = new Label(
                "Sudoku with custom size" +
                        "\nSize is shown in boxes, a normal sudoku is size 3, i.e. 3x3 = 9x9 cells." +
                        "\n\nUniqueness is only relevant for sudoku's of size 6x6 and above." +
                        "\nForcing uniqueness on larger sizes will result in a significant delay.");
        customDescLabel.setVisible(false);

        Label customDisclaimerLabel = new Label(
                "KEYBINDS - size 4x4 and above:" +
                        "\nUse 'Enter' to apply the numbers" +
                        "\nUse 'Backspace' to delete numbers/digits" +
                        "\n\nDISCLAIMER" +
                        "\nNumbers not applied with 'Enter' are only visual" +
                        "\nand are thus not checked by the solver." +
                        "\nThis can lead to a completed puzzle without" +
                        "\nthe finale prompt and mistakes not counted properly.");
        customDisclaimerLabel.setVisible(false);

        TextField customSizeField = new TextField("3");
        customSizeField.setVisible(false);

        submitSizeBtn.setVisible(false);
        lifeCheckBox.setVisible(false);
        uniqueCheckBox.selectedProperty().set(true);
        uniqueCheckBox.setVisible(false);

        // Back to main menu button
        Button backToMenu = new Button("Back to Main Menu");
        String buttonStyle1 = "-fx-background-color: white; -fx-text-fill: black; "
                + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;";

        backToMenu.setStyle(buttonStyle1);
        classicBtn.setStyle(buttonStyle1);
        samuraiBtn.setStyle(buttonStyle1);
        easyBtn.setStyle(buttonStyle1);
        mediumBtn.setStyle(buttonStyle1);
        hardBtn.setStyle(buttonStyle1);
        CampaignBtn.setStyle(buttonStyle1);

        customSizeBtn.setStyle(buttonStyle1);
        submitSizeBtn.setStyle(buttonStyle1);

        backToMenu.setOnMouseEntered(e -> backToMenu.setStyle(buttonStyle1 + hoverStyle));
        backToMenu.setOnMouseExited(e -> backToMenu.setStyle(buttonStyle1));

        classicBtn.setOnMouseEntered(e -> {
            classicBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            imageView.setVisible(true); // Gør imageView synlig
            classicDescLabel.setVisible(true); // Gør descriptionLabel synlig
            customDescLabel.setVisible(false);
            customDisclaimerLabel.setVisible(false);
            submitSizeBtn.setVisible(false);
            customSizeField.setVisible(false);
            lifeCheckBox.setVisible(false);
            uniqueCheckBox.setVisible(false);
        });
        classicBtn.setOnMouseExited(e -> {
            classicBtn.setStyle(buttonStyle1); // Gendan knapstil
            imageView.setVisible(false); // Gør imageView usynlig
            classicDescLabel.setVisible(false); // Gør descriptionLabel usynlig
        });

        samuraiBtn.setOnMouseEntered(e -> {
            samuraiBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            SamuraiView.setVisible(true); // Gør imageView synlig
            samuraiDescLabel.setVisible(true); // Gør descriptionLabel synlig
            customDescLabel.setVisible(false);
            customDisclaimerLabel.setVisible(false);
            submitSizeBtn.setVisible(false);
            customSizeField.setVisible(false);
            lifeCheckBox.setVisible(false);
            uniqueCheckBox.setVisible(false);
        });
        samuraiBtn.setOnMouseExited(e -> {
            samuraiBtn.setStyle(buttonStyle1); // Gendan knapstil
            SamuraiView.setVisible(false); // Gør imageView usynlig
            samuraiDescLabel.setVisible(false); // Gør descriptionLabel usynlig
        });

        easyBtn.setOnMouseEntered(e -> {
            easyBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            EasyView.setVisible(true); // Gør imageView synlig
            easyDescLabel.setVisible(true); // Gør descriptionLabel synlig
            customDescLabel.setVisible(false);
            customDisclaimerLabel.setVisible(false);
            submitSizeBtn.setVisible(false);
            customSizeField.setVisible(false);
            lifeCheckBox.setVisible(false);
            uniqueCheckBox.setVisible(false);
        });
        easyBtn.setOnMouseExited(e -> {
            easyBtn.setStyle(buttonStyle1); // Gendan knapstil
            EasyView.setVisible(false); // Gør imageView usynlig
            easyDescLabel.setVisible(false); // Gør descriptionLabel usynlig
        });

        mediumBtn.setOnMouseEntered(e -> {
            mediumBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            MediumView.setVisible(true); // Gør imageView synlig
            mediumDescLabel.setVisible(true); // Gør descriptionLabel synlig
            customDescLabel.setVisible(false);
            customDisclaimerLabel.setVisible(false);
            submitSizeBtn.setVisible(false);
            customSizeField.setVisible(false);
            lifeCheckBox.setVisible(false);
            uniqueCheckBox.setVisible(false);
        });
        mediumBtn.setOnMouseExited(e -> {
            mediumBtn.setStyle(buttonStyle1); // Gendan knapstil
            MediumView.setVisible(false); // Gør imageView usynlig
            mediumDescLabel.setVisible(false); // Gør descriptionLabel usynlig
        });

        hardBtn.setOnMouseEntered(e -> {
            hardBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            HardView.setVisible(true); // Gør imageView synlig
            hardDescLabel.setVisible(true); // Gør descriptionLabel synlig
            customDescLabel.setVisible(false);
            customDisclaimerLabel.setVisible(false);
            submitSizeBtn.setVisible(false);
            customSizeField.setVisible(false);
            lifeCheckBox.setVisible(false);
            uniqueCheckBox.setVisible(false);
        });
        hardBtn.setOnMouseExited(e -> {
            hardBtn.setStyle(buttonStyle1); // Gendan knapstil
            HardView.setVisible(false); // Gør imageView usynlig
            hardDescLabel.setVisible(false); // Gør descriptionLabel usynlig
        });
        customSizeBtn.setOnMouseEntered(e -> {
            customSizeBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            customDescLabel.setVisible(true);
            customDisclaimerLabel.setVisible(true);
            customSizeField.setVisible(true); // Gør descriptionLabel og textfield synlig
            submitSizeBtn.setVisible(true);
            lifeCheckBox.setVisible(true);
            uniqueCheckBox.setVisible(true);
            ;
        });
        customSizeBtn.setOnMouseExited(e -> {
            customSizeBtn.setStyle(buttonStyle1); // Gendan knapstil
        });

        CampaignBtn.setOnMouseEntered(e -> {
            CampaignBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            CampaignView.setVisible(true); // Gør imageView synlig
            campaignDescLabel.setVisible(true); // Gør descriptionLabel synlig
            customDescLabel.setVisible(false);
            customDisclaimerLabel.setVisible(false);
            submitSizeBtn.setVisible(false);
            customSizeField.setVisible(false);
            lifeCheckBox.setVisible(false);
            uniqueCheckBox.setVisible(false);
        });
        CampaignBtn.setOnMouseExited(e -> {
            CampaignBtn.setStyle(buttonStyle1); // Gendan knapstil
            CampaignView.setVisible(false); // Gør imageView usynlig
            campaignDescLabel.setVisible(false); // Gør descriptionLabel usynlig
        });

        // Event handler for buttons

        backToMenu.setOnAction(arg0 -> {
            settingStage.close();
            MainMenu.mainMenuStage.show();
        });

        submitSizeBtn.setOnAction(arg0 -> {
            // få fat i størrelsen på custom board
            String input = customSizeField.getText();
            if (input.length() == 0) {
                size = "3";
            } else if (input.length() == 1) {
                size = "" + customSizeField.getText().charAt(0);
            } else if (input.length() > 1) {
                size = "" + customSizeField.getText().charAt(0) + customSizeField.getText().charAt(1);
            }
            try {
                // Opret en ny instans af SudokuBoard
                SudokuBoard sudokuBoard = new SudokuBoard(Integer.parseInt(size));
                SudokuBoard.returnContext = "mainMenu";

                Stage sudokuStage = new Stage();
                if (lifeCheckBox.isSelected()) {
                    SudokuBoard.lifeOn = true;
                } else {
                    SudokuBoard.lifeOn = false;
                }
                if (uniqueCheckBox.isSelected()) {
                    SudokuBoard.unique = true;
                } else {
                    SudokuBoard.unique = false;
                }
                BasicBoard.difficulty = "Custom";
                sudokuBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close();

        });

        classicBtn.setOnAction(arg0 -> {
            try {
                // Opret en ny instans af SudokuBoard
                SudokuBoard sudokuBoard = new SudokuBoard(3);
                SudokuBoard.returnContext = "mainMenu";

                Stage sudokuStage = new Stage();
                SudokuBoard.lifeOn = false;
                BasicBoard.difficulty = "Classic";
                sudokuBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close();
        });
        easyBtn.setOnAction(arg0 -> {
            try {
                // Opret en ny instans af SudokuBoard
                SudokuBoard sudokuBoard = new SudokuBoard(3);
                SudokuBoard.returnContext = "mainMenu";

                Stage sudokuStage = new Stage();
                SudokuBoard.lifeOn = true;
                SudokuBoard.mistakes = 0;
                BasicBoard.difficulty = "Easy";
                sudokuBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close();
        });
        mediumBtn.setOnAction(arg0 -> {
            try {
                // Opret en ny instans af SudokuBoard
                SudokuBoard sudokuBoard = new SudokuBoard(3);
                SudokuBoard.returnContext = "mainMenu";

                Stage sudokuStage = new Stage();
                SudokuBoard.lifeOn = true;
                SudokuBoard.mistakes = 0;
                BasicBoard.difficulty = "Medium";
                sudokuBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close();
        });
        hardBtn.setOnAction(arg0 -> {
            try {
                // Opret en ny instans af SudokuBoard
                SudokuBoard sudokuBoard = new SudokuBoard(3);
                SudokuBoard.returnContext = "mainMenu";

                Stage sudokuStage = new Stage();
                SudokuBoard.lifeOn = true;
                SudokuBoard.mistakes = 0;
                BasicBoard.difficulty = "Hard";
                sudokuBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close();
        });

        samuraiBtn.setOnAction(arg0 -> {
            try {
                // Opret en ny instans af SudokuBoard
                SamuraiBoard samuraiBoard = new SamuraiBoard(3);

                Stage sudokuStage = new Stage();
                SamuraiBoard.lifeOn = true;
                SamuraiBoard.mistakes = 0;
                BasicBoard.difficulty = "Medium";
                samuraiBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close();
        });

        CampaignBtn.setOnAction(arg0 -> {
            CampaignMenu campaignMenu = new CampaignMenu();
            campaignMenu.showCampaign();
            settingStage.close();
        });

        customSizeBtn.setOnAction(arg0 -> {
            customSizeField.setVisible(true);
        });

        StackPane layout = new StackPane();
        layout.getChildren().addAll(imageView, classicBtn, backToMenu, classicDescLabel, samuraiBtn, easyBtn, mediumBtn,
                hardBtn, SamuraiView, samuraiDescLabel, EasyView, easyDescLabel, MediumView, mediumDescLabel, HardView,
                hardDescLabel, customSizeBtn, customDescLabel, customDisclaimerLabel, customSizeField, submitSizeBtn,
                CampaignBtn,
                CampaignView, campaignDescLabel, lifeCheckBox, uniqueCheckBox);
        StackPane.setMargin(imageView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(SamuraiView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(CampaignView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(EasyView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(MediumView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(HardView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(descriptionLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(campaignDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(classicBtn, new javafx.geometry.Insets(100, 600, 600, 100));
        StackPane.setMargin(samuraiBtn, new javafx.geometry.Insets(700, 600, 600, 100));
        StackPane.setMargin(CampaignBtn, new javafx.geometry.Insets(850, 600, 600, 100));
        StackPane.setMargin(backToMenu, new javafx.geometry.Insets(600, 100, 0, 100));
        StackPane.setMargin(classicDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(samuraiDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(easyDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(mediumDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(hardDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(classicBtn, new javafx.geometry.Insets(100, 600, 600, 100));
        StackPane.setMargin(samuraiBtn, new javafx.geometry.Insets(700, 600, 600, 100));
        StackPane.setMargin(backToMenu, new javafx.geometry.Insets(600, 100, 0, 100));
        StackPane.setMargin(easyBtn, new javafx.geometry.Insets(250, 600, 600, 100));
        StackPane.setMargin(mediumBtn, new javafx.geometry.Insets(400, 600, 600, 100));
        StackPane.setMargin(hardBtn, new javafx.geometry.Insets(550, 600, 600, 100));
        StackPane.setMargin(customSizeBtn, new javafx.geometry.Insets(400, 600, 0, 100));
        StackPane.setMargin(customDescLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(customDisclaimerLabel, new javafx.geometry.Insets(200, 0, 200, 200));
        StackPane.setMargin(customSizeField, new javafx.geometry.Insets(0, 200, 300, 300));
        StackPane.setMargin(lifeCheckBox, new javafx.geometry.Insets(50, 200, 300, 300));
        StackPane.setMargin(uniqueCheckBox, new javafx.geometry.Insets(100, 200, 300, 300));
        StackPane.setMargin(submitSizeBtn, new javafx.geometry.Insets(600, 200, 300, 300));
        Scene scene = new Scene(layout, sizeX, sizeY);
        settingStage.setScene(scene);
        settingStage.centerOnScreen();
        settingStage.show();

    }

}
