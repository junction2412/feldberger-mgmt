package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.UserSession;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.NavContext;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The actual entry point of the application UI.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class ApplicationNavContext implements NavContext<ApplicationNavRoute> {

    private final FXControllerFactory fxControllerFactory;
    private final ApplicationTransitionFactory transitionFactory;
    private Stage stage;

    public ApplicationNavContext(FXControllerFactory fxControllerFactory,
                                 ApplicationTransitionFactory transitionFactory) {

        this(fxControllerFactory, transitionFactory, null);
    }

    public ApplicationNavContext(FXControllerFactory fxControllerFactory,
                                 ApplicationTransitionFactory transitionFactory,
                                 Stage stage) {

        this.fxControllerFactory = fxControllerFactory;
        this.transitionFactory = transitionFactory;
        this.stage = stage;
    }

    @Override
    public void navigateTo(ApplicationNavRoute route) {

        if (stage == null)
            throw new NullPointerException("Cannot navigate because stage is null.");

        var parent = switch (route) {
            case LoginForm form -> login(form.username());
            case RegistrationForm form -> registration(form.username());
            case UserSession session -> mainMenu(session.userID(), session.username());
        };

        setSceneRoot(parent);
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    private Parent login(String username) {

        final var loginTransition = transitionFactory.loginSession(session -> Platform.runLater(() ->
                navigateTo(session)));

        final var registrationTransition = transitionFactory.loginRegistration(form -> Platform.runLater(() ->
                navigateTo(form)));

        final FXController controller = fxControllerFactory.login(
                loginTransition,
                registrationTransition,
                username
        );

        return controller.load();
    }

    private Parent registration(String username) {

        final var registrationTransition = transitionFactory.registrationSession(session -> Platform.runLater(() ->
                navigateTo(session)));

        final var loginTransition = transitionFactory.registrationLogin(form -> Platform.runLater(() ->
                navigateTo(form)));

        final FXController controller = fxControllerFactory.registration(
                registrationTransition,
                loginTransition,
                username
        );

        return controller.load();
    }

    private Parent mainMenu(int userID, String username) {

        final var logoutTransition = transitionFactory.sessionLogin(form -> Platform.runLater(() ->
                navigateTo(form)));

        final var settingsTransition = TransitionOrchestrator.<UserSession>immediate(_ -> System.out.println("NOOP"));

        final FXController controller = fxControllerFactory.mainMenu(
                logoutTransition,
                settingsTransition,
                userID, username
        );

        return controller.load();
    }

    private void setSceneRoot(Parent parent) {

        if (stage.getScene() == null)
            stage.setScene(new Scene(parent));
        else
            stage.getScene().setRoot(parent);
    }
}
