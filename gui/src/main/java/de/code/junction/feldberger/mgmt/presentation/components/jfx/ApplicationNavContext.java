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

        final var controller = switch (route) {
            case LoginForm form -> login(form.username());
            case RegistrationForm form -> registration(form.username());
            case UserSession session -> mainMenu(session.userID(), session.username());
        };

        setSceneController(controller);
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    private FXController login(String username) {

        final var loginTransition = transitionFactory.loginSession(this::navigateTo);
        final var registrationTransition = transitionFactory.loginRegistration(this::navigateTo);

        return fxControllerFactory.login(loginTransition, registrationTransition, username);
    }

    private FXController registration(String username) {

        final var registrationTransition = transitionFactory.registrationSession(this::navigateTo);
        final var loginTransition = transitionFactory.registrationLogin(this::navigateTo);

        return fxControllerFactory.registration(registrationTransition, loginTransition, username);
    }

    private FXController mainMenu(int userID, String username) {

        final var logoutTransition = transitionFactory.sessionLogin(this::navigateTo);
        final var settingsTransition = TransitionOrchestrator.<UserSession>immediate(_ -> System.out.println("NOOP"));

        return fxControllerFactory.mainMenu(logoutTransition, settingsTransition, userID, username);
    }

    private void setSceneController(FXController controller) {

        final Runnable runnable = () -> setSceneRoot(controller.load());

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }

    private void setSceneRoot(Parent parent) {

        if (stage.getScene() == null)
            stage.setScene(new Scene(parent));
        else
            stage.getScene().setRoot(parent);
    }
}
