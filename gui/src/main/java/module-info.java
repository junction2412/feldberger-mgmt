module de.code.junction.feldberger.mgmt.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.code.junction.feldberger.mgmt.representation to javafx.fxml;
    exports de.code.junction.feldberger.mgmt.representation;
}