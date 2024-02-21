package dk.dtu.View;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SudokuBoard extends Application {

    public static Stage boardStage = new Stage();
    int sizeX = 1400;
    int sizeY = 900;

    // Application layout
    BorderPane borderPane = new BorderPane();
    GridPane pane = new GridPane();
    public static HBox bottomHUD = new HBox();


    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Sudoku game");

        








        boardStage.show();
    }
    
}
