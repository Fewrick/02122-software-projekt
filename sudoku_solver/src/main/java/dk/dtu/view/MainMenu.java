package dk.dtu.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;

import javafx.stage.Stage;

/**
 * JavaFX App here
 */
public class MainMenu extends Application {

    private static int sizeX = 700;
    private static int sizeY = 700;
    public Button ExitBtn = new Button("Exit");
    public Button Rules = new Button("Rules");
    public Button GameSetting = new Button("New Game");
    public Button leaderboardBtn = new Button("Show Leaderboard");

    public static String buttonStyle;
    public static String hoverStyle;

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
        buttonStyle = "-fx-background-color: white; -fx-text-fill: black; "
                + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        ExitBtn.setStyle(buttonStyle);
        Rules.setStyle(buttonStyle);
        GameSetting.setStyle(buttonStyle);
        leaderboardBtn.setStyle(buttonStyle);

        // Hover effect
        hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        ExitBtn.setOnMouseEntered(e -> ExitBtn.setStyle(buttonStyle + hoverStyle));
        ExitBtn.setOnMouseExited(e -> ExitBtn.setStyle(buttonStyle));
        Rules.setOnMouseEntered(e -> Rules.setStyle(buttonStyle + hoverStyle));
        Rules.setOnMouseExited(e -> Rules.setStyle(buttonStyle));
        GameSetting.setOnMouseEntered(e -> GameSetting.setStyle(buttonStyle + hoverStyle));
        GameSetting.setOnMouseExited(e -> GameSetting.setStyle(buttonStyle));
        leaderboardBtn.setOnMouseEntered(e -> leaderboardBtn.setStyle(buttonStyle + hoverStyle));
        leaderboardBtn.setOnMouseExited(e -> leaderboardBtn.setStyle(buttonStyle));

        // Button actions
        GameSetting.setOnAction(arg0 -> {
            GameSettingsMenu gameSetting = new GameSettingsMenu();
            gameSetting.GameSettings();
            mainMenuStage.close();
        });

        ExitBtn.setOnAction(arg0 -> {
            mainMenuStage.close();
        });

        Rules.setOnAction(arg0 -> {
            GameRulesMenu gameRules = new GameRulesMenu();
            gameRules.showGameRules();
            mainMenuStage.close();
        });

        leaderboardBtn.setOnAction(arg0 -> {
            System.out.println("Fetching leaderboard...");
            Leaderboard.showLeaderboard(null);
        });

        // Layout
        StackPane layout = new StackPane();
        layout.getChildren().add(imageView);
        layout.getChildren().addAll(ExitBtn, Rules, GameSetting, leaderboardBtn);

        StackPane.setAlignment(ExitBtn, Pos.CENTER_RIGHT);
        StackPane.setAlignment(Rules, Pos.CENTER_LEFT);
        StackPane.setAlignment(GameSetting, Pos.CENTER);
        StackPane.setAlignment(leaderboardBtn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(ExitBtn, new javafx.geometry.Insets(500, 100, 0, 100)); // Juster top- og venstremarginen
                                                                                    // efter behov
        StackPane.setMargin(Rules, new javafx.geometry.Insets(500, 100, 0, 100));
        StackPane.setMargin(GameSetting, new javafx.geometry.Insets(500, 0, 0, 0));
        StackPane.setMargin(leaderboardBtn, new javafx.geometry.Insets(600, 100, 0, 100));

        // Create and set scene
        Scene scene = new Scene(layout, sizeX, sizeY);
        mainMenuStage.setScene(scene);
        mainMenuStage.show();
    }
}
