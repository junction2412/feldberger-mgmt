package de.code.junction.feldberger.mgmt.presentation.view;

import de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class Controller {

    private final String fxmlFileName;

    public Controller(String fxmlFileName) {

        this.fxmlFileName = fxmlFileName;
    }

    public final Parent load() {

        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        fxmlLoader.setController(this);

        final Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        translate(ResourceUtil.getLabelStringResources());

        return parent;
    }

    @FXML
    protected abstract void initialize();

    protected abstract void translate(ResourceBundle bundle);
}
