package dk.dtu.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameRulesMenu {
    private int sizeX = 700;
    private int sizeY = 700;
    private TextArea text = new TextArea();

    public void showGameRules() {
        Stage rulesStage = new Stage();
        StageStyle style = StageStyle.DECORATED;
        rulesStage.initStyle(style);
        rulesStage.setTitle("Rules");
        rulesStage.show();

        rulesStage.setWidth(sizeX);
        rulesStage.setHeight(sizeY);

        text.setEditable(false);
        text.setWrapText(true);
        text.setFont(new Font("Arial", 20));
        text.setText("Rules of sudoku: " +
                     "\n\nPlaying the game: " +
                     "\nTap an empty cell on the board to highlight it and use the numbers on your keyboard to enter different numbers. \nBlack numbers are puzzle generated numbers and thus cant be removed, user-placed numbers are grey." +
                     "\n\nKeybings: " +
                     "\nPress 'Enter' to apply the number " +
                     "\nPress 'Backspace' to delete numbers/digits " +
                     "\n\nDISCLAIMER\nNumbers not applied with 'Enter' are only visual and are thus not checked by the solver, which can lead to a completed puzzle without the finale prompt og mistakes not counted properly.");
        
        //Back to main menu button
        Button backToMenu = new Button("Back to Main Menu");
        String buttonStyle1 = "-fx-background-color: white; -fx-text-fill: black; "
            + "-fx-font-size: 1.5em; -fx-min-width: 150px; -fx-min-height: 50px; "
            + "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";
            String hoverStyle = "-fx-scale-x: 1.1; -fx-scale-y: 1.1;";
        
        backToMenu.setStyle(buttonStyle1);
        backToMenu.setOnMouseEntered(e -> backToMenu.setStyle(buttonStyle1 + hoverStyle));
        backToMenu.setOnMouseExited(e -> backToMenu.setStyle(buttonStyle1));

        // Button behavior
        backToMenu.setOnAction(arg0 -> {
            rulesStage.close();
            MainMenu.mainMenuStage.show();
        });
        
        StackPane layout = new StackPane();
        layout.getChildren().addAll(text,backToMenu);
        StackPane.setMargin(backToMenu, new javafx.geometry.Insets(600, 100, 0, 100)); 
        Scene scene = new Scene(layout, sizeX, sizeY);
        rulesStage.setScene(scene);
        rulesStage.centerOnScreen();
        rulesStage.show();

    }
}