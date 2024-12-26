package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.cache.Cache;
import de.code.junction.feldberger.mgmt.presentation.cache.ScopeName;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.RouteStack;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.UserSession;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * The actual entry point of the application UI.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class ApplicationNavContext extends RouteStack<Stage, ApplicationRoute> {

    private final ApplicationControllerFactory controllerFactory;
    private final ApplicationTransitionFactory transitionFactory;

    public ApplicationNavContext(Stack<Route<ApplicationRoute>> stack,
                                 ApplicationControllerFactory controllerFactory,
                                 ApplicationTransitionFactory transitionFactory) {

        super(stack);

        this.controllerFactory = controllerFactory;
        this.transitionFactory = transitionFactory;
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

        final var loginTransition = transitionFactory.login(route -> {

            final var userId = (int) route.cache().get("userId");
            final var routes = Cache.<ApplicationRoute>getScopeRoutes(userId, ScopeName.APPLICATION);

            if (!routes.isEmpty()) {

                final var cachedRoute = routes.peek();

                if (cachedRoute.name() == ApplicationRoute.MAIN_MENU) {

                    final var subview = (String) cachedRoute.cache().getOrDefault(
                            "selectedSubviewEnumValue",
                            null
                    );

                    if (subview != null)
                        route.cache().put("selectedSubviewEnumValue", subview);
                }
            }

            push(route);
        });

        final Consumer<String> onRegisterClicked = username -> {
            final var _cache = new HashMap<String, Object>();
            _cache.put("username", username);

            push(new Route<>(ApplicationRoute.REGISTRATION, _cache));
        };

        return controllerFactory.login(loginTransition::orchestrate, onRegisterClicked, cache);
    }

    private FXController mainMenu(HashMap<String, Object> cache) {

        final Consumer<UserSession> onSettingsClicked = _ -> System.out.println("Settings");

        return controllerFactory.mainMenu(this::pop, onSettingsClicked, cache);
    }

    private FXController registration(Map<String, Object> cache) {

        final var registrationTransition = transitionFactory.registration(this::swap);

        return controllerFactory.registration(this::pop, registrationTransition::orchestrate, cache);
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
