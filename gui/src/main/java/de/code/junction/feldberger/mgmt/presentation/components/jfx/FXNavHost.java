package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManager;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManagerFactory;
import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXNavHost {

    private final FXControllerFactory fxControllerFactory;
    private final TransitionManagerFactory transitionManagerFactory;
    private final Stage stage;

    public FXNavHost(FXControllerFactory fxControllerFactory,
                     TransitionManagerFactory transitionManagerFactory,
                     Stage stage) {

        this.fxControllerFactory = fxControllerFactory;
        this.transitionManagerFactory = transitionManagerFactory;
        this.stage = stage;
    }

    public void start() {

        login("");
        stage.setMaximized(true);
        stage.show();
    }

    private void login(String username) {

        final TransitionManager<Credentials> mainMenuTransitionManager = transitionManagerFactory.loginToMainMenu(_ ->
                System.out.println("NOOP"));

        final LoginController controller = fxControllerFactory.login(
                mainMenuTransitionManager,
                this::registration,
                username
        );

        final Parent parent = controller.load();

        if (stage.getScene() == null)
            stage.setScene(new Scene(parent));
        else
            stage.getScene().setRoot(parent);
    }

    private void registration(String username) {

        final TransitionManager<RegistrationForm> mainMenuTransitionManager = transitionManagerFactory.registrationToMainMenu(_ ->
                System.out.println("NOOP"));

        final RegistrationController controller = fxControllerFactory.registration(
                mainMenuTransitionManager,
                this::login,
                username
        );

        final Parent parent = controller.load();

        stage.getScene().setRoot(parent);
    }
}
