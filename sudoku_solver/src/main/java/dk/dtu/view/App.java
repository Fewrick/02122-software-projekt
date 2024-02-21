package dk.dtu.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

/**
 * JavaFX App here
 */
public class App extends Application {

    private static int sizeX = 700;
    private static int sizeY = 700;
    public Button Btn = new Button();
    public Button StartGameBtn = new Button();
    public static Stage mainMenuStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        mainMenuStage = primaryStage;
        // Application layout
        mainMenuStage.setTitle("Main Menu");

        Btn.setText("SUDOKU");

        StartGameBtn.setText("Start Game");
        StartGameBtn.setOnAction(arg0 -> {
            try {
                SudokuBoardMenu(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(Btn, StartGameBtn); // Knap oven på billedet
        StackPane.setAlignment(Btn, Pos.TOP_CENTER);
        StackPane.setMargin(Btn, new javafx.geometry.Insets(260, 0, 0, 0));

        Btn.setStyle("-fx-background-color: #5DADE2; -fx-text-fill: white; "
        + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 25px; "
        + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
// Opret Scene med StackPane og sæt den til Stage
        Scene scene2 = new Scene(stackPane, sizeX, sizeY);
        mainMenuStage.setScene(scene2);
        mainMenuStage.show();
    }

    private void SudokuBoardMenu(ActionEvent event) throws Exception {
        SudokuBoard sudokuBoard = new SudokuBoard();
        try {
            sudokuBoard.start(SudokuBoard.boardStage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SudokuBoard.boardStage.show();
        mainMenuStage.close();
    }

}