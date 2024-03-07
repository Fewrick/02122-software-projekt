package dk.dtu;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import dk.dtu.view.MainMenu;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) throws UnknownHostException, SocketException {
        // // Launch the Lobby Application (start window)
        System.out.println("Starting application...");
        try {
            // Connect to the database or create it if it doesn't exist
            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            System.out.println("Connection to SQLite has been established.");

            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS leaderboard (\n"
                    + " id integer PRIMARY KEY,\n"
                    + " name text NOT NULL,\n"
                    + " time text NOT NULL,\n"
                    + " difficulty text NOT NULL\n"
                    + ");";
            stmt.execute(sql);
            // Close the connection
            // conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Application.launch(MainMenu.class, args);
    
    }
}