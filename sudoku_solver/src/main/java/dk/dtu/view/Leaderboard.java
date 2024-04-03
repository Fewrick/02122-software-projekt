package dk.dtu.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Leaderboard {

    static Button mediumButton = new Button("Sort by Medium");
    static Button classicButton = new Button("Sort by Classic");
    public static ScrollPane scrollPane;
    public static GridPane gridPane;
    private static Stage stage = null;

    public static void showLeaderboard(String difficulty) {
        try {
            // If the stage is already showing, bring it to front and return
            if (stage != null && stage.isShowing()) {
                gridPane.getChildren().clear();
                stage.toFront();
                return;
            } else {
                scrollPane = new ScrollPane();
                gridPane = new GridPane();
                gridPane.setPadding(new Insets(10));
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                scrollPane.setContent(gridPane);
            }

            // Connect to the database
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://cornelius.db.elephantsql.com:5432/bvdlelci", "bvdlelci",
                    "B1QrdKqxmTmhI1qgLU-XnZvRoIdC8fzq");

            // Execute a SELECT query and get the result set
            String query = difficulty == null ? "SELECT * FROM leaderboard ORDER BY time ASC"
                    : "SELECT * FROM leaderboard WHERE difficulty = ? ORDER BY time ASC";
            PreparedStatement pStatement = conn.prepareStatement(query);
            if (difficulty != null) {
                pStatement.setString(1, difficulty);
            }
            ResultSet rs = pStatement.executeQuery();

            // Variables to keep track of the Text with the lowest time
            gridPane.add(new Text("Name:"), 0, 0);
            gridPane.add(new Text("Time:"), 1, 0);
            gridPane.add(new Text("Difficulty:"), 2, 0);

            Text[] lowestTimeTexts = new Text[3];
            String[] lowestTimes = new String[3];

            // Process the result set
            int row = 1;
            System.out.println("Leaderboard loaded");
            while (rs.next()) {
                String name = rs.getString("name");
                String time = rs.getString("time");
                String getDifficulty = rs.getString("difficulty");

                // Create new Texts for each record and add them to the GridPane
                Text nameText = new Text(name);
                Text timeText = new Text(time);
                Text difficultyText = new Text(getDifficulty);
                gridPane.add(nameText, 0, row);
                gridPane.add(timeText, 1, row);
                gridPane.add(difficultyText, 2, row);

                for (int i = 0; i < 3; i++) {
                    if (lowestTimes[i] == null || time.compareTo(lowestTimes[i]) < 0) {
                        // Shift the lower times down
                        for (int j = 2; j > i; j--) {
                            lowestTimes[j] = lowestTimes[j - 1];
                            lowestTimeTexts[j] = lowestTimeTexts[j - 1];
                        }
                        // Insert the new time
                        lowestTimes[i] = time;
                        lowestTimeTexts[i] = timeText;
                        break;
                    }
                }

                row++;
            }
            // Set the fill color of the lowest time Texts to gold, silver, and bronze
            // and increase their font size
            if (lowestTimeTexts[0] != null) {
                lowestTimeTexts[0].setFill(Color.GOLD);
                lowestTimeTexts[0].setFont(new Font(20));
            }
            if (lowestTimeTexts[1] != null) {
                lowestTimeTexts[1].setFill(Color.SILVER);
                lowestTimeTexts[1].setFont(new Font(17));
            }
            if (lowestTimeTexts[2] != null) {
                lowestTimeTexts[2].setFill(Color.DARKORANGE);
                lowestTimeTexts[2].setFont(new Font(15));
            }

            // Button styling
            mediumButton.setStyle(MainMenu.buttonStyle);
            mediumButton.setOnMouseEntered(e -> mediumButton.setStyle(MainMenu.buttonStyle + MainMenu.hoverStyle));
            mediumButton.setOnMouseExited(e -> mediumButton.setStyle(MainMenu.buttonStyle));

            classicButton.setStyle(MainMenu.buttonStyle);
            classicButton.setOnMouseEntered(e -> classicButton.setStyle(MainMenu.buttonStyle + MainMenu.hoverStyle));
            classicButton.setOnMouseExited(e -> classicButton.setStyle(MainMenu.buttonStyle));

            // Action listeners for the buttons
            mediumButton.setOnAction(event -> {
                if (stage != null && stage.isShowing()) {
                    stage.close();
                }
                showLeaderboard("Medium");
            });
            classicButton.setOnAction(event -> {
                if (stage != null && stage.isShowing()) {
                    stage.close();
                }
                showLeaderboard("Classic");
            });

            // Add buttons to a layout
            HBox buttonBox = new HBox(mediumButton, classicButton);

            // Add the button box and the grid pane to a VBox
            VBox vbox = new VBox(buttonBox, scrollPane);

            // Create a new Scene with the VBox as the root node
            Scene scene = new Scene(vbox, 400, 200);

            // Create a new Stage to show the Scene
            stage = new Stage();
            stage.setTitle("Leaderboard");
            stage.setScene(scene);
            stage.show();
            conn.close();

            // Reset stage variabl
            // stage = null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
