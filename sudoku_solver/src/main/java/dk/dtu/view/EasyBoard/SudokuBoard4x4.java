package dk.dtu.view.EasyBoard;

import dk.dtu.controller.DFSSolver;
import dk.dtu.controller.SudokuButton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuBoard4x4 extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 800; // Tilpasset for et 4x4 grid
    static int sizeY = 800;
    static int gridSize = 4; // 4x4 grid
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    BorderPane borderPane = new BorderPane();
    public static GridPane pane = new GridPane();
    VBox bottom = new VBox();
    Button solveSudoku = new Button("Solve!!");
   // Button backtoMenu = new Button("Back to Main Menu");

    @Override
    public void start(Stage stage) {
        boardStage = stage;
        boardStage.setTitle("4x4 Sudoku Game");

        setupBoard();
        // addEventHandlers();

        boardStage.show();

        solveSudoku.setOnAction(arg0 -> {
            try {
                DFSSolver.solveSudoku(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setupBoard() {
        
        bottom.setAlignment(Pos.CENTER); // Dette centrerer knapperne i VBox
        bottom.getChildren().add(solveSudoku);

        // Sørger for at GridPane (Sudoku boardet) bliver centreret i BorderPane
        pane.setAlignment(Pos.CENTER); // Centrerer GridPane
        BorderPane.setAlignment(pane, Pos.CENTER); // Sikrer at GridPane er centreret i BorderPane

        // Opretter Sudoku boardet
        BasicBoard4x4.createSudoku(pane);

        // Justerer GridPane størrelsen hvis nødvendigt
        pane.setPrefSize(sizeX * 0.5, sizeY * 0.5); // Juster størrelsen baseret på dine behov

        // Sætter BorderPane og scenen op
        borderPane.setCenter(pane);
        borderPane.setBottom(bottom);

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

    }
}
