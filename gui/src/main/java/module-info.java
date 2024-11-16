module de.code.junction.feldberger.mgmt.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires de.code.junction.feldbergermgmt.repository;
    requires java.sql;
    requires jdk.jfr;

    exports de.code.junction.feldberger.mgmt.presentation;

    opens de.code.junction.feldberger.mgmt.presentation to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.model to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.login to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.registration to javafx.fxml;
}