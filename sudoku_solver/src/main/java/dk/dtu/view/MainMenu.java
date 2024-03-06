package dk.dtu.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import dk.dtu.controller.Generator;

/**
 * JavaFX App here
 */
public class MainMenu extends Application {

    private static int sizeX = 700;
    private static int sizeY = 700;
    public Button ExitBtn = new Button("Exit");
    public Button Rules = new Button("Rules");
    public Button GenerateSudokuBtn = new Button("Generate Sudoku");
    public Button GameSetting = new Button("Game Setting");
    public static Stage mainMenuStage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        mainMenuStage = primaryStage;
        mainMenuStage.setTitle("Main Menu");
        

        Image backgroundImage = new Image(getClass().getResourceAsStream("/dk/dtu/view/image/Sudokumenuuu.png"));
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setFitWidth(sizeX);
        imageView.setFitHeight(sizeY);
        imageView.setPreserveRatio(false);

        // Button styles
        String buttonStyle = "-fx-background-color: white; -fx-text-fill: black; "
                + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        ExitBtn.setStyle(buttonStyle);
        Rules.setStyle(buttonStyle);
        GenerateSudokuBtn.setStyle(buttonStyle);
        GameSetting.setStyle(buttonStyle);
        
        // Hover effect
        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        ExitBtn.setOnMouseEntered(e -> ExitBtn.setStyle(buttonStyle + hoverStyle));
        ExitBtn.setOnMouseExited(e -> ExitBtn.setStyle(buttonStyle));
        Rules.setOnMouseEntered(e -> Rules.setStyle(buttonStyle + hoverStyle));
        Rules.setOnMouseExited(e -> Rules.setStyle(buttonStyle));
        GenerateSudokuBtn.setOnMouseEntered(e -> GenerateSudokuBtn.setStyle(buttonStyle + hoverStyle));
        GenerateSudokuBtn.setOnMouseExited(e -> GenerateSudokuBtn.setStyle(buttonStyle));

        GameSetting.setOnMouseEntered(e -> GameSetting.setStyle(buttonStyle + hoverStyle));
        GameSetting.setOnMouseExited(e -> GameSetting.setStyle(buttonStyle));
        
        // Button actions
        GameSetting.setOnAction(arg0 -> {
            GameSettingsMenu gameSetting = new GameSettingsMenu();
            gameSetting.GameSettings();
            mainMenuStage.close();
        });
        
        ExitBtn.setOnAction(arg0 -> {
            mainMenuStage.close();
        });
        GenerateSudokuBtn.setOnAction(arg0 -> {
            try {
                Generator.GenerateSudoku(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }});
        
        Rules.setOnAction(arg0 -> {
            GameRulesMenu menu2 = new GameRulesMenu();
            menu2.showGameRules();
            mainMenuStage.close();
        });

        // Layout
        StackPane layout = new StackPane();
        layout.getChildren().add(imageView); 
        layout.getChildren().addAll(ExitBtn, Rules, GameSetting, GenerateSudokuBtn);
        
        StackPane.setAlignment(ExitBtn, Pos.CENTER_LEFT);
        StackPane.setAlignment(Rules, Pos.CENTER_RIGHT);
        StackPane.setAlignment(GameSetting, Pos.CENTER);
        StackPane.setAlignment(GenerateSudokuBtn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(ExitBtn, new javafx.geometry.Insets(500, 100, 0, 100)); // Juster top- og venstremarginen efter behov
        StackPane.setMargin(Rules, new javafx.geometry.Insets(500, 100, 0, 100)); 
        StackPane.setMargin(GameSetting, new javafx.geometry.Insets(500, 0, 0, 0));

        // Create and set scene
        Scene scene = new Scene(layout, sizeX, sizeY);
        mainMenuStage.setScene(scene);
        mainMenuStage.show();
    }
} 

