module de.code.junction.feldberger.mgmt.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires de.code.junction.feldbergermgmt.repository;
    requires java.sql;

    opens de.code.junction.feldberger.mgmt.presentation to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.login to javafx.fxml;
    exports de.code.junction.feldberger.mgmt.presentation;
}