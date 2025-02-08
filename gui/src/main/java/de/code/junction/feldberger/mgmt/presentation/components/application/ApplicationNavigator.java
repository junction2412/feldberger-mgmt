package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.components.ViewFactory;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.*;

/**
 * The actual entry point of the application UI.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class ApplicationNavigator implements Navigator<ApplicationRoute> {

    private final Stage stage;
    private final ViewFactory viewFactory;

    public ApplicationNavigator(Stage stage, ViewFactory viewFactory) {
        this.stage = stage;
        this.viewFactory = viewFactory;
    }

    @Override
    public void navigateTo(ApplicationRoute route) {

        final var view = switch (route) {
            case Registration(String username) -> viewFactory.registration(this, username);
            case Login(String username) -> viewFactory.login(this, username);
            case MainMenu(int userId, String username) -> viewFactory.mainMenu(this, userId, username);
        };

        final var parent = view.load();

        if (stage.getScene() == null)
            stage.setScene(new Scene(parent));
        else
            stage.getScene().setRoot(parent);
    }

}
