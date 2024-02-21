module dk.dtu {
    requires javafx.controls;
    requires javafx.fxml;

    opens dk.dtu to javafx.fxml;
    exports dk.dtu;
}
