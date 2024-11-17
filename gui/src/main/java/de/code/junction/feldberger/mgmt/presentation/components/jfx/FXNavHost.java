package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.model.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
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

        final TransitionOrchestrator<LoginForm, User> loginTransition = transitionFactory
                .loginTransition(_ -> System.out.println("NOOP"));

        final FXController controller = fxControllerFactory.login(
                loginTransition,
                TransitionOrchestrator.immediate(this::registration),
                username
        );

        final Parent parent = controller.load();

        if (stage.getScene() == null)
            stage.setScene(new Scene(parent));
        else
            stage.getScene().setRoot(parent);
    }

    private void registration(String username) {

        final TransitionOrchestrator<RegistrationForm, User> registrationTransition = transitionFactory
                .registrationTransition(_ -> System.out.println("NOOP"));

        final FXController controller = fxControllerFactory.registration(
                registrationTransition,
                TransitionOrchestrator.immediate(this::login),
                username
        );

        final Parent parent = controller.load();

        stage.getScene().setRoot(parent);
    }
}
