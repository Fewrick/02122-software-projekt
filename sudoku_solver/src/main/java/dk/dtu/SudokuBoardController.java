package dk.dtu;

import java.io.IOException;
import javafx.fxml.FXML;

public class SudokuBoardController {

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
}