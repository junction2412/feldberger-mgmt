package de.code.junction.feldberger.mgmt.presentation.view;

/**
 * The preferred class for JavaFX controllers as repetitive loading boilerplate is leveraged in a streamlined manner.
 *
 * @author J. Murray
 */
@Deprecated
public abstract class FXController implements FXLoadable {

    /**
     * The name of the adjacent fxml file.
     */
    private final String fxmlFileName;

    public FXController(String fxmlFileName) {

        this.fxmlFileName = fxmlFileName;
    }

    @Override
    public String fxml() {

        return fxmlFileName;
    }
}
