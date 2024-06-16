package dk.dtu.view.samurai;

import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.PuzzleGenerator;
import dk.dtu.view.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SudokuSamuraiBoard extends Application {
    public static Stage boardStage = new Stage();
    static int sizeX = 1200;  // Adjusted for better fit of samurai sudoku
    static int sizeY = 900;   // Adjusted for better fit of samurai sudoku

    static Button solveSudoku = new Button("Solve Sudoku");
    public Button backToMenu = new Button("Back to Main Menu");

    @Override
    public void start(Stage stage) throws Exception {
        boardStage = stage;
        boardStage.setTitle("Samurai Sudoku");

        BorderPane borderPane = new BorderPane();
        Pane mainPane = new Pane();
        mainPane.setPrefSize(sizeX, sizeY);

        // Generate Samurai Sudoku data
        int[][][] samuraiData = PuzzleGenerator.generateSamuraiSudoku();
        
        // Create and display the Samurai Sudoku grids
        SamuraiBasicBoard.createSamuraiSudoku(mainPane, samuraiData);
        
        // Styling for buttons
        solveSudoku.setStyle("-fx-font-size: 16px;");
        backToMenu.setStyle("-fx-font-size: 16px;");

        HBox bottomPanel = new HBox(10);
        bottomPanel.getChildren().addAll(backToMenu, solveSudoku);
        bottomPanel.setStyle("-fx-padding: 10px; -fx-alignment: center;");

        // Add components to BorderPane
        borderPane.setCenter(mainPane);
        borderPane.setBottom(bottomPanel);

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);
        boardStage.show();

        // Set the actions for buttons
        solveSudoku.setOnAction(event -> {
            // Implement solving logic or call a method to solve the Sudoku
            //BasicBoard.showSolution(mainPane);
        });

        backToMenu.setOnAction(event -> {
            boardStage.close();
            MainMenu.mainMenuStage.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
