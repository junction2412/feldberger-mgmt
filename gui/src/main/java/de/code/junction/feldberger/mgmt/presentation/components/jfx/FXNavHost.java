package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.domain.UserViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The FXNavHost is the actual entry point of the application UI. Though I might reconsider it sooner or later.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class FXNavHost {

    private final FXControllerFactory fxControllerFactory;
    private final TransitionFactory transitionFactory;
    private Stage stage;

    public FXNavHost(FXControllerFactory fxControllerFactory,
                     TransitionFactory transitionFactory) {

        this(fxControllerFactory, transitionFactory, null);
    }

    public FXNavHost(FXControllerFactory fxControllerFactory,
                     TransitionFactory transitionFactory,
                     Stage stage) {

        this.fxControllerFactory = fxControllerFactory;
        this.transitionFactory = transitionFactory;
        this.stage = stage;
    }

    public void start() {

        if (stage == null)
            throw new NullPointerException("Cannot start because stage is null.");

        login("");
        stage.setMaximized(true);
        stage.show();
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    private void login(String username) {

        final var loginTransition = transitionFactory
                .loginTransition(user -> Platform.runLater(() -> mainMenu(user.getID(), user.getUsername())));

        final var registrationTransition = TransitionOrchestrator.<String>immediate(_username -> Platform.runLater(() ->
                registration(_username)));

        final FXController controller = fxControllerFactory.login(
                loginTransition,
                registrationTransition,
                username
        );

        final Parent parent = controller.load();

        if (stage.getScene() == null)
            stage.setScene(new Scene(parent));
        else
            stage.getScene().setRoot(parent);
    }

    private void registration(String username) {

        final var registrationTransition = transitionFactory.registrationTransition(user -> Platform.runLater(() ->
                mainMenu(user.getID(), user.getUsername())));

        final FXController controller = fxControllerFactory.registration(
                registrationTransition,
                TransitionOrchestrator.immediate(_username -> Platform.runLater(() -> login(_username))),
                username
        );

        final Parent parent = controller.load();

        stage.getScene().setRoot(parent);
    }

    private void mainMenu(int userID, String username) {

        final var logoutTransition = TransitionOrchestrator.<String>immediate(_username -> Platform.runLater(() ->
                login(_username)));

        final var settingsTransition = TransitionOrchestrator.<UserViewModel>immediate(_ -> System.out.println("NOOP"));

        final FXController controller = fxControllerFactory.mainMenu(
                logoutTransition,
                settingsTransition,
                userID, username
        );

        final Parent parent = controller.load();

        stage.getScene().setRoot(parent);
    }
}
