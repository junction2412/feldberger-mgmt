package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.components.ViewFactory;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.*;

/**
 * The actual entry point of the application UI.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class ApplicationNavigator extends ScopedNavigator<Stage, ApplicationRoute> {

    private final ViewFactory viewFactory;

    public ApplicationNavigator(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    @Override
    public void navigateTo(ApplicationRoute route) {

        final var controller = switch (route) {
            case Registration(String username) -> viewFactory.registration(username);
            case Login(String username) -> viewFactory.login(username);
            case MainMenu(int userId, String username) -> viewFactory.mainMenu(userId, username);
        };

        setSceneController(controller);
    }

    private void setSceneController(FXLoadable controller) {

        final Runnable runnable = () -> setSceneRoot(controller.load());

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }

    private void setSceneRoot(Parent parent) {

        if (scope.getScene() == null)
            scope.setScene(new Scene(parent));
        else
            scope.getScene().setRoot(parent);
    }
}
