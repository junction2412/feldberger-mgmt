package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceRegistry;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.UserSession;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.Consumer;
import java.util.function.Function;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.*;

/**
 * The actual entry point of the application UI.
 * It mainly defines in-app navigation based on fixed routes.
 *
 * @author J. Murray
 */
public class ApplicationNavContext extends ScopedNavContext<Stage, ApplicationRoute> {

    private final ApplicationControllerFactory controllerFactory;
    private final ApplicationTransitionFactory transitionFactory;
    private PreferenceRegistry preferenceRegistry;

    public ApplicationNavContext(ApplicationControllerFactory controllerFactory,
                                 ApplicationTransitionFactory transitionFactory) {

        this.controllerFactory = controllerFactory;
        this.transitionFactory = transitionFactory;
    }

    @Override
    public void navigateTo(ApplicationRoute route) {

        final var controller = switch (route) {
            case Registration registration -> registration(registration.username());
            case Login login -> login(login.username());
            case MainMenu mainMenu -> mainMenu(mainMenu.user());
        };

        setSceneController(controller);
    }

    private FXController login(String username) {

        final var loginTransition = transitionFactory.login(this::navigateTo);

        final Function<String, Registration> factory = Registration::new;
        final Consumer<Registration> action = this::navigateTo;
        final Consumer<String> onRegisterClicked = _username -> action.accept(factory.apply(_username));

        return controllerFactory.login(loginTransition::orchestrate, onRegisterClicked, username);
    }

    private FXController mainMenu(User user) {

        final Consumer<UserSession> onSettingsClicked = _ -> System.out.println("Settings");

        final Runnable logout = () -> navigateTo(new Login(user.getUsername()));

        final var persistenceManager = PersistenceManager.getInstance();
        final var userReference = persistenceManager.userDao().getReference(user.getId());
        preferenceRegistry = new PreferenceRegistry(persistenceManager.preferenceDao(), userReference);

        return controllerFactory.mainMenu(logout, onSettingsClicked, user, preferenceRegistry);
    }

    private FXController registration(String username) {

        final var registrationTransition = transitionFactory.registration(this::navigateTo);

        final Consumer<String> login = _username -> navigateTo(new Login(_username));
        return controllerFactory.registration(login, registrationTransition::orchestrate, username);
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

    public PreferenceRegistry getPreferenceRegistry() {

        return preferenceRegistry;
    }
}
