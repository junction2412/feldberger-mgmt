package de.code.junction.feldberger.mgmt.presentation.view;

import de.code.junction.feldberger.mgmt.presentation.view.login.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getLabelStringResources;

/// The [View] is the root class of all displayed UI elements in Model-View-ViewModel.
/// It is only aware of what its [ViewModel] exposes, though the [ViewModel] manages interaction with actual business logic.
///
/// @see LoginView
public interface View<V extends ViewModel> extends FXLoadable {

    /// Simple view model accessor
    V viewModel();

    @Override
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

        viewModel().init();

        return parent;
    }
}
