package dk.dtu;

import java.io.IOException;
import javafx.fxml.FXML;

public class SudokuBoard {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}