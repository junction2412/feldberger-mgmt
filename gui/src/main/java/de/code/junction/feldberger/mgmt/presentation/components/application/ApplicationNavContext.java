package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.UserSession;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactoryProvider;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
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
public class ApplicationNavContext extends ScopedNavContext<Stage, ApplicationNavRoute> {

    private final ApplicationControllerFactory controllerFactory;
    private final TransitionFactoryProvider transitionFactoryProvider;
    private final ApplicationTransitionFactory transitionFactory;

    public ApplicationNavContext(ApplicationControllerFactory controllerFactory,
                                 TransitionFactoryProvider transitionFactoryProvider) {

        this.controllerFactory = controllerFactory;
        this.transitionFactoryProvider = transitionFactoryProvider;
        transitionFactory = transitionFactoryProvider.applicationTransitionFactory();
    }

    @Override
    public void navigateTo(ApplicationNavRoute route) {

        if (scope == null)
            throw new NullPointerException("Cannot navigate because scope is null.");

        final var controller = switch (route) {
            case LoginForm form -> login(form.username());
            case RegistrationForm form -> registration(form.username());
            case UserSession session -> mainMenu(session.userId(), session.username());
        };

        setSceneController(controller);
    }

    private FXController login(String username) {

        final var loginTransition = transitionFactory.loginSession(this::navigateTo);
        final var registrationTransition = transitionFactory.loginRegistration(this::navigateTo);

        return controllerFactory.login(loginTransition, registrationTransition, username);
    }

    private FXController registration(String username) {

        final var registrationTransition = transitionFactory.registrationSession(this::navigateTo);
        final var loginTransition = transitionFactory.registrationLogin(this::navigateTo);

        return controllerFactory.registration(registrationTransition, loginTransition, username);
    }

    private FXController mainMenu(int userID,
                                  String username) {

        final var logoutTransition = transitionFactory.sessionLogin(this::navigateTo);
        final var settingsTransition = Transition.<UserSession>immediate(_ -> System.out.println("NOOP"));

        return controllerFactory.mainMenu(
                transitionFactoryProvider.mainMenuTransitionFactory(),
                logoutTransition,
                settingsTransition,
                userID,
                username
        );
    }

    private void setSceneController(FXController controller) {

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
