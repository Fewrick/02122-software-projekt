package dk.dtu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import dk.dtu.view.MainMenu;
import javafx.application.Application;

// Used for sql statements
// import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        // // Launch the Lobby Application (start window)
        System.out.println("Starting application...");
        try {
            // Connect to the database or create it if it doesn't exist
            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://cornelius.db.elephantsql.com:5432/bvdlelci", "bvdlelci",
                    "B1QrdKqxmTmhI1qgLU-XnZvRoIdC8fzq");
            System.out.println("Connection to ElephantSQL was successful!");

            // ------------------------------------------------------------------------------------------
            // //
            // Uncomment following block to create the table if it doesnt exist, or drop the
            // current one //
            // ------------------------------------------------------------------------------------------
            // //

            // Create the table if it doesn't exist or drop the current one
            // Statement stmt = conn.createStatement();
            // stmt.execute("DROP TABLE IF EXISTS leaderboard");
            // String sql = "CREATE TABLE IF NOT EXISTS leaderboard (\n"
            // + " id SERIAL PRIMARY KEY,\n"
            // + " name text NOT NULL,\n"
            // + " time text NOT NULL,\n"
            // + " difficulty text NOT NULL\n"
            // + ");";
            // stmt.execute(sql);

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Application.launch(MainMenu.class, args);

    }
}