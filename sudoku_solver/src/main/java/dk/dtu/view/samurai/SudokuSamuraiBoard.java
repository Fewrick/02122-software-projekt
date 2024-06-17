package dk.dtu.view.samurai;

import dk.dtu.controller.SudokuButton;
import dk.dtu.view.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuSamuraiBoard extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 800;
    static int sizeY = 800;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;
    static Button button1 = new Button();
    static Button button2 = new Button();
    static Button button3 = new Button();
    static Button button4 = new Button();
    static Button button5 = new Button();
    static Button button6 = new Button();
    static Button button7 = new Button();
    static Button button8 = new Button();
    static Button button9 = new Button();

    static Button solveSudoku = new Button("Solve!!");
    public Button backtoMenu = new Button("Back to Main Menu");

    static SudokuButton[][] buttons2D = new SudokuButton[gridSize][gridSize];

    // Application layout
    BorderPane borderPane = new BorderPane();
    public static GridPane pane = new GridPane();
    VBox leftVbox = new VBox();
    VBox rightVbox = new VBox();
    VBox TopVbox = new VBox();
    public static HBox bottom = new HBox();

    @Override
    public void start(Stage stage) throws Exception {
    boardStage = stage;
    boardStage.setTitle("Samurai Sudoku");

    // Opretter et større pane til at indeholde de fem Sudoku grids
    Pane mainPane = new Pane();
    mainPane.setPrefSize(sizeX, sizeY);
   
    //Buttons
    String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
    + "-fx-font-size: 1.3em; -fx-min-width: 130px; -fx-min-height: 40px; "
    + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";

    backtoMenu.setStyle(buttonStyle);
    solveSudoku.setStyle(buttonStyle);

    String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover

    backtoMenu.setOnMouseEntered(e -> backtoMenu.setStyle(buttonStyle + hoverStyle));
    backtoMenu.setOnMouseExited(e -> backtoMenu.setStyle(buttonStyle));
    solveSudoku.setOnMouseEntered(e -> solveSudoku.setStyle(buttonStyle + hoverStyle));
    solveSudoku.setOnMouseExited(e -> solveSudoku.setStyle(buttonStyle));

    HBox.setMargin(backtoMenu, new javafx.geometry.Insets(0, 400, 15, 40));
    HBox.setMargin(solveSudoku, new javafx.geometry.Insets(0, 0, 15, 40));

    // Opdater denne metode til at oprette og arrangere de fem GridPane objekter
    SamuraiBasicBoard.createSamuraiSudoku(mainPane);

    // Initialiser resten af borderPane layout
    bottom.getChildren().addAll(backtoMenu, solveSudoku);    
    borderPane.setBottom(bottom);
    borderPane.setCenter(mainPane); // Brug mainPane med Sudoku grids som centrum

    Scene scene = new Scene(borderPane, sizeX, sizeY);
    boardStage.setScene(scene);
    boardStage.show();

    // Event handlers
    solveSudoku.setOnAction(arg0 -> {
        try {
            // DFSSolver.solveSudoku(arg0); Outdated kode
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    backtoMenu.setOnAction(arg0 -> {
        boardStage.close();
        MainMenu.mainMenuStage.show();
    });
}

public static void blackBorder(SudokuButton[][] buttons, int row, int column) {
    SudokuButton button = buttons[row][column];

    // Add black borders to separate 3x3 boxes
    if ((column + 1) % Math.sqrt(gridSize) == 0 && column + 1 != gridSize) {
        button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 0 0;");
    }
    if ((row + 1) % Math.sqrt(gridSize) == 0 && row + 1 != gridSize) {
        button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 0 3px 0;");
    }
    if ((column + 1) % Math.sqrt(gridSize) == 0 && column != gridSize - 1 && (row + 1) % Math.sqrt(gridSize) == 0
            && row != gridSize - 1) {
        button.setStyle(button.getStyle() + "; -fx-border-color: black; -fx-border-width: 0 3px 3px 0;");
    }
}
}
