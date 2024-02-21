package dk.dtu.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.MalformedURLException;

/**
 * JavaFX App here
 */
public class App extends Application {

    private static int sizeX = 700;
    private static int sizeY = 700;
    public Button ExitBtn = new Button("Exit");
    public Button StartGameBtn = new Button("Start Game");
    public Button Rules = new Button("Rules");
    public static Stage mainMenuStage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        mainMenuStage = primaryStage;
        mainMenuStage.setTitle("Main Menu");
        

        Image backgroundImage = new Image(getClass().getResourceAsStream("/dk/dtu/view/image/Sudokumenuuu.png"));
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setFitWidth(sizeX);
        imageView.setFitHeight(sizeY);
        imageView.setPreserveRatio(false);

        // Button styles
        String buttonStyle = "-fx-background-color: white; -fx-text-fill: black; "
                + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
                + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
        ExitBtn.setStyle(buttonStyle);
        StartGameBtn.setStyle(buttonStyle);
        Rules.setStyle(buttonStyle);
        
        // Hover effect
        String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;"; // Enlarge buttons on hover
        ExitBtn.setOnMouseEntered(e -> ExitBtn.setStyle(buttonStyle + hoverStyle));
        ExitBtn.setOnMouseExited(e -> ExitBtn.setStyle(buttonStyle));
        StartGameBtn.setOnMouseEntered(e -> StartGameBtn.setStyle(buttonStyle + hoverStyle));
        StartGameBtn.setOnMouseExited(e -> StartGameBtn.setStyle(buttonStyle));
        Rules.setOnMouseEntered(e -> Rules.setStyle(buttonStyle + hoverStyle));
        Rules.setOnMouseExited(e -> Rules.setStyle(buttonStyle));

        // Button actions
        StartGameBtn.setOnAction(arg0 -> {
            try {
                SudokuBoardMenu(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ExitBtn.setOnAction(arg0 -> {
            mainMenuStage.close();
        });
        // Layout
        StackPane layout = new StackPane();
        layout.getChildren().add(imageView); 
        layout.getChildren().addAll(ExitBtn, StartGameBtn, Rules);
        
        StackPane.setAlignment(ExitBtn, Pos.CENTER_LEFT);
        StackPane.setAlignment(Rules, Pos.CENTER_RIGHT);
        StackPane.setAlignment(StartGameBtn, Pos.CENTER);
        StackPane.setMargin(ExitBtn, new javafx.geometry.Insets(500, 100, 0, 100)); // Juster top- og venstremarginen efter behov
        StackPane.setMargin(Rules, new javafx.geometry.Insets(500, 100, 0, 100)); 
        StackPane.setMargin(StartGameBtn, new javafx.geometry.Insets(500, 0, 0, 0));

        // Create and set scene
        Scene scene = new Scene(layout, sizeX, sizeY);
        mainMenuStage.setScene(scene);
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

