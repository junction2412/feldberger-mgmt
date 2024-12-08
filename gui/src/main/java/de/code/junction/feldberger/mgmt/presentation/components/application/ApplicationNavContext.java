package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactoryProvider;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.RouteStack;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.UserSession;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The actual entry point of the application UI.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class ApplicationNavContext extends RouteStack<Stage, ApplicationRoute> {

    private final ApplicationControllerFactory controllerFactory;
    private final TransitionFactoryProvider transitionFactoryProvider;
    private final ApplicationTransitionFactory transitionFactory;

    public ApplicationNavContext(Stack<Route<ApplicationRoute>> stack,
                                 ApplicationControllerFactory controllerFactory,
                                 TransitionFactoryProvider transitionFactoryProvider) {

        super(stack);
        this.controllerFactory = controllerFactory;
        this.transitionFactoryProvider = transitionFactoryProvider;
        this.transitionFactory = transitionFactoryProvider.applicationTransitionFactory();
    }

    @Override
    public void navigateTo(Route<ApplicationRoute> route) {

        final var controller = switch (route.name()) {
            case REGISTRATION -> registration(route.cache());
            case LOGIN -> login(route.cache());
            case MAIN_MENU -> mainMenu(route.cache());
        };

        setSceneController(controller);
    }

    private FXController login(Map<String, Object> cache) {

        final var loginTransition = transitionFactory.login(this::push);
        final var registrationTransition = Transition.<LoginForm, Route<ApplicationRoute>>bypass(
                form -> {
                    final var registrationCache = new HashMap<String, Object>();
                    registrationCache.put("username", form.username());

                    return new Route<>(
                            ApplicationRoute.REGISTRATION,
                            registrationCache
                    );
                },
                this::push
        );

        return controllerFactory.login(
                loginTransition,
                registrationTransition,
                cache
        );
    }

    private FXController mainMenu(HashMap<String, Object> cache) {

        final var logoutTransition = Transition.<UserSession, Route<ApplicationRoute>>bypass(
                session -> {
                    final var loginCache = new HashMap<String, Object>();
                    loginCache.put("username", session.username());
                    return new Route<>(ApplicationRoute.LOGIN, loginCache);
                },
                this::push
        );

        return controllerFactory.mainMenu(
                transitionFactoryProvider.mainMenuTransitionFactory(),
                logoutTransition,
                cache
        );
    }

    private FXController registration(Map<String, Object> cache) {

        final var registrationTransition = transitionFactory.registration(this::push);

        return controllerFactory.registration(
                registrationTransition,
                Transition.immediate(_ -> pop()),
                cache
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
