package dk.dtu.view.easy;

import dk.dtu.controller.BasicBoard;
import dk.dtu.controller.DFSSolver;
import dk.dtu.controller.SudokuButton;
import dk.dtu.view.MainMenu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SudokuBoard4x4 extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 800; // Tilpasset for et 4x4 grid
    static int sizeY = 800;
    static int gridSize = 4; // 4x4 grid
    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    BorderPane borderPane = new BorderPane();
    public static GridPane pane = new GridPane();
    HBox bottom = new HBox();
    static Button solveSudoku = new Button("Solve!!");
    static Button backtoMenu = new Button("Back to Main Menu");
    SudokuButton Button = new SudokuButton(0);
    public static int[][] puzzleBoard;

    
    @Override
    public void start(Stage stage) {
        boardStage = stage;
        boardStage.setTitle("4x4 Sudoku Game");

        setupBoard();
        // addEventHandlers();
        SudokuBoard4x4.solveSudoku.setOnAction(arg0 -> {
                    try {
                        DFSSolver.solveSudoku(arg0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                SudokuBoard4x4.backtoMenu.setOnAction(arg0 -> {
                    MainMenu mainMenu = new MainMenu();
                    try {
                        mainMenu.start(SudokuBoard4x4.boardStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

        boardStage.show();
    }

    private void setupBoard() {
        
        String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
                + "-fx-font-size: 1.3em; -fx-min-width: 130px; -fx-min-height: 40px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        solveSudoku.setStyle(buttonStyle);
        backtoMenu.setStyle(buttonStyle);

        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        solveSudoku.setOnMouseEntered(e -> solveSudoku.setStyle(buttonStyle + hoverStyle));
        solveSudoku.setOnMouseExited(e -> solveSudoku.setStyle(buttonStyle));
        backtoMenu.setOnMouseEntered(e -> backtoMenu.setStyle(buttonStyle + hoverStyle));
        backtoMenu.setOnMouseExited(e -> backtoMenu.setStyle(buttonStyle));

        bottom.setAlignment(Pos.CENTER); // Dette centrerer knapperne i VBox
        bottom.getChildren().addAll(backtoMenu, solveSudoku);
        HBox.setMargin(backtoMenu, new javafx.geometry.Insets(0, 300, 80, 0));
        HBox.setMargin(solveSudoku, new javafx.geometry.Insets(0, 0, 80, 0));

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
