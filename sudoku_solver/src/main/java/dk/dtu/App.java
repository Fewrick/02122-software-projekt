package dk.dtu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static int sizeX = 700;
    private static int sizeY = 700;
    public Button Btn = new Button();
    public static Stage mainMenuStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainMenuStage = primaryStage;
        // Application layout
        mainMenuStage.setTitle("Main Menu");

        Button Btn = new Button();
        Btn.setText("button");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(Btn); // Knap oven på billedet
        StackPane.setAlignment(Btn, Pos.TOP_CENTER);

// Opret Scene med StackPane og sæt den til Stage
        Scene scene2 = new Scene(stackPane, sizeX, sizeY);
        mainMenuStage.setScene(scene2);
        mainMenuStage.show();
    }

}