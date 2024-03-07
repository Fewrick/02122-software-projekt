package dk.dtu.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Leaderboard {

    public static void showLeaderboard() {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");

            // Create a new statement
            Statement stmt = conn.createStatement();

            // Execute a SELECT query and get the result set
            ResultSet rs = stmt.executeQuery("SELECT * FROM leaderboard ORDER BY time ASC");

            // Create a new ScrollPane and GridPane to hold the leaderboard
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            scrollPane.setContent(gridPane);

            // Variables to keep track of the Text with the lowest time

            gridPane.add(new Text("Name:"), 0, 0);
            gridPane.add(new Text("Time:"), 1, 0);
            gridPane.add(new Text("Difficulty:"), 2, 0);

            Text[] lowestTimeTexts = new Text[3];
            String[] lowestTimes = new String[3];

            // Process the result set
            int row = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                String time = rs.getString("time");
                String difficulty = rs.getString("difficulty");

                // Create new Texts for each record and add them to the GridPane
                Text nameText = new Text(name);
                Text timeText = new Text(time);
                Text difficultyText = new Text(difficulty);
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
            if (lowestTimeTexts[0] != null)
                lowestTimeTexts[0].setFill(Color.GOLD);
            if (lowestTimeTexts[1] != null)
                lowestTimeTexts[1].setFill(Color.SILVER);
            if (lowestTimeTexts[2] != null)
                lowestTimeTexts[2].setFill(Color.DARKORANGE);

            // Create a new Scene with the ScrollPane as the root node
            Scene scene = new Scene(scrollPane, 300, 200);

            // Create a new Stage to show the Scene
            Stage stage = new Stage();
            stage.setTitle("Leaderboard");
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}