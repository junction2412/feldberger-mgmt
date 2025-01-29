package de.code.junction.feldberger.mgmt.presentation.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getLabelStringResources;

///  A default interface that is used to streamline view initialization.
///
/// @author J. Murray
public interface FXLoadable {

    /// @return name of the .fxml-file that is loaded
    String fxml();

    /// @return initialized parent
    default Parent load() {

        final var fxmlLoader = new FXMLLoader(getClass().getResource(fxml()), getLabelStringResources());
        fxmlLoader.setController(this);

        final Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            // pretty rare here
            throw new RuntimeException(e);
        }

        return parent;
    }

    /// JavaFX specific initialization logic.
    @FXML
    void initialize();
}
