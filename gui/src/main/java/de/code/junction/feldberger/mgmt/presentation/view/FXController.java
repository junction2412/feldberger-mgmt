package de.code.junction.feldberger.mgmt.presentation.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getLabelStringResources;

/**
 * The preferred class for JavaFX controllers as repetitive loading boilerplate is leveraged in a streamlined manner.
 *
 * @author J. Murray
 */
public abstract class FXController {

    /**
     * The name of the adjacent fxml file.
     */
    private final String fxmlFileName;

    public FXController(String fxmlFileName) {

        this.fxmlFileName = fxmlFileName;
    }

    /**
     * Load the fxml contents, initialize the view and translate labels.
     *
     * @return initialized parent
     */
    public final Parent load() {

        final var fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName), getLabelStringResources());
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

    /**
     * JavaFX specific initialization.
     */
    @FXML
    protected abstract void initialize();
}
