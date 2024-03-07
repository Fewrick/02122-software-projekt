package dk.dtu.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App here
 */
public class MainMenu extends Application {

    private static int sizeX = 700;
    private static int sizeY = 700;
    public Button ExitBtn = new Button("Exit");
    public Button Rules = new Button("Rules");
    public Button GameSetting = new Button("Game Settings");
    public Button leaderboardBtn = new Button("Show Leaderboard");

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
        GameSetting.setStyle(buttonStyle);
        leaderboardBtn.setStyle(buttonStyle);

        // Hover effect
        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
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
            GameRulesMenu menu2 = new GameRulesMenu();
            menu2.showGameRules();
            mainMenuStage.close();
        });

        leaderboardBtn.setOnAction(arg0 -> {
            try {
                // Connect to the database
                Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");

                // Create a new statement
                Statement stmt = conn.createStatement();

                // Execute a SELECT query and get the result set
                ResultSet rs = stmt.executeQuery("SELECT * FROM leaderboard ORDER BY time ASC");

                // Create a new VBox to hold the leaderboard
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10));
                vbox.setSpacing(8);

                // Process the result set
                while (rs.next()) {
                    String name = rs.getString("name");
                    String time = rs.getString("time");
                    String difficulty = rs.getString("difficulty");

                    // Create a new Text for each record and add it to the VBox
                    Text text = new Text("Name: " + name + ", Time: " + time + ", Difficulty: " + difficulty);
                    vbox.getChildren().add(text);
                }

                // Close the connection
                conn.close();

                // Create a new Scene with the VBox as the root node
                Scene scene = new Scene(vbox, 300, 200);

                // Create a new Stage to show the Scene
                Stage stage = new Stage();
                stage.setTitle("Leaderboard");
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
