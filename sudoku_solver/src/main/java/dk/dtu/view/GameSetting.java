package dk.dtu.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameSetting {

    private int sizeX = 700;
    private int sizeY = 700;
    public Button StartGameBtn = new Button("Classic");
    public Button Samurai = new Button("Samurai");
    public Button Easy = new Button("Easy");
    public Button Medium = new Button("Medium");
    public Button Hard = new Button("Hard");


    

    public void GameSettings() {

        Stage settingStage = new Stage();
        StageStyle style = StageStyle.DECORATED;
        settingStage.initStyle(style);
        settingStage.setTitle("Game Settings");
        settingStage.show();

        settingStage.setWidth(sizeX);
        settingStage.setHeight(sizeY);

        ImageView EasyView = new ImageView(new Image("dk/dtu/view/image/Easy.png"));
        EasyView.setFitWidth(400); // Sæt ønsket bredde
        EasyView.setFitHeight(400); // Sæt ønsket højde
        EasyView.setPreserveRatio(true);
        EasyView.setVisible(false);

        ImageView MediumView = new ImageView(new Image("dk/dtu/view/image/Medium.png"));
        MediumView.setFitWidth(400); // Sæt ønsket bredde
        MediumView.setFitHeight(400); // Sæt ønsket højde
        MediumView.setPreserveRatio(true);
        MediumView.setVisible(false);

        ImageView HardView = new ImageView(new Image("dk/dtu/view/image/Hard.png"));
        HardView.setFitWidth(400); // Sæt ønsket bredde
        HardView.setFitHeight(400); // Sæt ønsket højde
        HardView.setPreserveRatio(true);
        HardView.setVisible(false);

        ImageView SamuraiView = new ImageView(new Image("dk/dtu/view/image/Samurai-sudoku.png"));
        SamuraiView.setFitWidth(400); // Sæt ønsket bredde
        SamuraiView.setFitHeight(400); // Sæt ønsket højde
        SamuraiView.setPreserveRatio(true);
        SamuraiView.setVisible(false);

        ImageView imageView = new ImageView(new Image("dk/dtu/view/image/Classic.png"));
        imageView.setFitWidth(400); // Sæt ønsket bredde
        imageView.setFitHeight(400); // Sæt ønsket højde
        imageView.setPreserveRatio(true); 
        imageView.setVisible(false); 

        Label descriptionLabel = new Label("Dette er en klassisk Sudoku.");
        descriptionLabel.setVisible(false);

        Label descriptionLabel2 = new Label("Dette er en Samurai Sudoku.");
        descriptionLabel2.setVisible(false);

        Label descriptionLabel3 = new Label("Dette er en let Sudoku.");
        descriptionLabel3.setVisible(false);

        Label descriptionLabel4 = new Label("Dette er en medium Sudoku.");
        descriptionLabel4.setVisible(false);

        Label descriptionLabel5 = new Label("Dette er en svær Sudoku.");
        descriptionLabel5.setVisible(false);


         //Back to main menu button
        Button backToMenu = new Button("Back to Main Menu");
        String buttonStyle1 = "-fx-background-color: white; -fx-text-fill: black; "
            + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
            + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
            String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;";
        
        backToMenu.setStyle(buttonStyle1);
        StartGameBtn.setStyle(buttonStyle1);
        Samurai.setStyle(buttonStyle1);
        Easy.setStyle(buttonStyle1);
        Medium.setStyle(buttonStyle1);
        Hard.setStyle(buttonStyle1);


        backToMenu.setOnMouseEntered(e -> backToMenu.setStyle(buttonStyle1 + hoverStyle));
        backToMenu.setOnMouseExited(e -> backToMenu.setStyle(buttonStyle1));

        StartGameBtn.setOnMouseEntered(e -> {
            StartGameBtn.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            imageView.setVisible(true); // Gør imageView synlig
            descriptionLabel.setVisible(true); // Gør descriptionLabel synlig
        });
        StartGameBtn.setOnMouseExited(e -> {
            StartGameBtn.setStyle(buttonStyle1); // Gendan knapstil
            imageView.setVisible(false); // Gør imageView usynlig
            descriptionLabel.setVisible(false); // Gør descriptionLabel usynlig
        });

        Samurai.setOnMouseEntered(e -> {
            Samurai.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            SamuraiView.setVisible(true); // Gør imageView synlig
            descriptionLabel2.setVisible(true); // Gør descriptionLabel synlig
        });
        Samurai.setOnMouseExited(e -> {
            Samurai.setStyle(buttonStyle1); // Gendan knapstil
            SamuraiView.setVisible(false); // Gør imageView usynlig
            descriptionLabel2.setVisible(false); // Gør descriptionLabel usynlig
        });

        Easy.setOnMouseEntered(e -> {
            Easy.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            EasyView.setVisible(true); // Gør imageView synlig
            descriptionLabel3.setVisible(true); // Gør descriptionLabel synlig
        });
        Easy.setOnMouseExited(e -> {
            Easy.setStyle(buttonStyle1); // Gendan knapstil
            EasyView.setVisible(false); // Gør imageView usynlig
            descriptionLabel3.setVisible(false); // Gør descriptionLabel usynlig
        });

        Medium.setOnMouseEntered(e -> {
            Medium.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            MediumView.setVisible(true); // Gør imageView synlig
            descriptionLabel4.setVisible(true); // Gør descriptionLabel synlig
        });
        Medium.setOnMouseExited(e -> {
            Medium.setStyle(buttonStyle1); // Gendan knapstil
            MediumView.setVisible(false); // Gør imageView usynlig
            descriptionLabel4.setVisible(false); // Gør descriptionLabel usynlig
        });

        Hard.setOnMouseEntered(e -> {
            Hard.setStyle(buttonStyle1 + hoverStyle); // Ændre knapstil
            HardView.setVisible(true); // Gør imageView synlig
            descriptionLabel5.setVisible(true); // Gør descriptionLabel synlig
        });
        Hard.setOnMouseExited(e -> {
            Hard.setStyle(buttonStyle1); // Gendan knapstil
            HardView.setVisible(false); // Gør imageView usynlig
            descriptionLabel5.setVisible(false); // Gør descriptionLabel usynlig
        });

        backToMenu.setOnAction(arg0 -> {
            settingStage.close();
            App.mainMenuStage.show();
        });

        StartGameBtn.setOnAction(arg0 -> {
            try {
                // Opret en ny instans af SudokuBoard
                SudokuBoard sudokuBoard = new SudokuBoard();

                Stage sudokuStage = new Stage();
                sudokuBoard.start(sudokuStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            settingStage.close(); 
        });
        
        
        StackPane layout = new StackPane();
        layout.getChildren().addAll(imageView, StartGameBtn, backToMenu, descriptionLabel, Samurai, Easy, Medium, Hard, SamuraiView, descriptionLabel2, EasyView, descriptionLabel3, MediumView, descriptionLabel4, HardView, descriptionLabel5);
        StackPane.setMargin(imageView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(SamuraiView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(EasyView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(MediumView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(HardView, new javafx.geometry.Insets(300, 200, 300, 400));
        StackPane.setMargin(descriptionLabel, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(descriptionLabel2, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(descriptionLabel3, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(descriptionLabel4, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(descriptionLabel5, new javafx.geometry.Insets(-150, 0, 300, 200));
        StackPane.setMargin(StartGameBtn, new javafx.geometry.Insets(100, 600, 600, 100));
        StackPane.setMargin(Samurai, new javafx.geometry.Insets(700, 600, 600, 100));
        StackPane.setMargin(backToMenu, new javafx.geometry.Insets(600, 100, 0, 100)); 
        StackPane.setMargin(Easy, new javafx.geometry.Insets(250, 600, 600, 100));
        StackPane.setMargin(Medium, new javafx.geometry.Insets(400, 600, 600, 100));
        StackPane.setMargin(Hard, new javafx.geometry.Insets(550, 600, 600, 100));
        Scene scene = new Scene(layout, sizeX, sizeY);
        settingStage.setScene(scene);
        settingStage.centerOnScreen();
        settingStage.show();
    
    }   

}
