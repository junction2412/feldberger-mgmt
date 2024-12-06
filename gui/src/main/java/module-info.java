module de.code.junction.feldberger.mgmt.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires de.code.junction.feldbergermgmt.repository;
    requires org.json;
    requires com.fasterxml.jackson.databind;

    exports de.code.junction.feldberger.mgmt.presentation;

    opens de.code.junction.feldberger.mgmt.presentation to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.login to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.registration to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.main.menu to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.customer.editor to javafx.fxml;
    opens de.code.junction.feldberger.mgmt.presentation.view.customer.overview to javafx.fxml;
}