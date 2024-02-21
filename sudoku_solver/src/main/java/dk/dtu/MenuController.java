package dk.dtu;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void switchToSudokuBoard() throws IOException {
        App.setRoot("sudokuboard");
    }
}
