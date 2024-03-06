package dk.dtu.view.medium;

import dk.dtu.controller.DFSSolver;
import dk.dtu.controller.SudokuButton;
import dk.dtu.view.MainMenu;
import dk.dtu.view.BasicBoard;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuBoard extends Application {

    public static Stage boardStage = new Stage();
    static int sizeX = 800;
    static int sizeY = 800;
    static int gridSize = 9;
    static int btnSize = sizeX / gridSize;

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
        boardStage.setTitle("Sudoku game");

        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        borderPane.setLeft(leftVbox);
        borderPane.setRight(rightVbox);
        borderPane.setTop(TopVbox);

        BasicBoard.createSudoku(pane,BasicBoard.board);

        // Constructs pane
        TopVbox.setPrefHeight(sizeY / 9 - 20);
        leftVbox.setPrefWidth(sizeX / 9 - 20);
        rightVbox.setPrefWidth(sizeX / 9 - 20);
        pane.setStyle("-fx-background-color: lightgrey;"); // Sets background color: Green

        Image backgroundImage = new Image(getClass().getResourceAsStream("/dk/dtu/view/image/image.png"));
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setFitWidth(sizeX - 500 );
        imageView.setFitHeight(sizeY / 9 );
        imageView.setPreserveRatio(false);
        TopVbox.getChildren().add(imageView);
        TopVbox.setAlignment(Pos.CENTER);

        String buttonStyle = "-fx-background-color: lightgrey; -fx-text-fill: black; "
                + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        solveSudoku.setStyle(buttonStyle);
        backtoMenu.setStyle(buttonStyle);

        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        solveSudoku.setOnMouseEntered(e -> solveSudoku.setStyle(buttonStyle + hoverStyle));
        solveSudoku.setOnMouseExited(e -> solveSudoku.setStyle(buttonStyle));
        backtoMenu.setOnMouseEntered(e -> backtoMenu.setStyle(buttonStyle + hoverStyle));
        backtoMenu.setOnMouseExited(e -> backtoMenu.setStyle(buttonStyle));

        Scene scene = new Scene(borderPane, sizeX, sizeY);
        boardStage.setScene(scene);

        bottom.setPrefHeight(sizeY / 9);
        bottom.getChildren().addAll(backtoMenu, solveSudoku);
        bottom.setAlignment(Pos.CENTER);
        HBox.setMargin(backtoMenu, new javafx.geometry.Insets(0, 300, 0, 50));
        HBox.setMargin(solveSudoku, new javafx.geometry.Insets(0, 50, 0, 0));

        boardStage.show();

        solveSudoku.setOnAction(arg0 -> {
            try {
                DFSSolver.solveSudoku(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        backtoMenu.setOnAction(arg0 -> {
            boardStage.close();
            bottom.getChildren().clear();
            MainMenu.mainMenuStage.show();
        });
    }

}
