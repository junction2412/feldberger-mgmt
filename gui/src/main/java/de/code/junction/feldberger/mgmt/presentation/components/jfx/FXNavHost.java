package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManager;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManagerFactory;
import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
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
    private final TransitionManagerFactory transitionManagerFactory;
    private Stage stage;

    public FXNavHost(FXControllerFactory fxControllerFactory,
                     TransitionManagerFactory transitionManagerFactory){

        this(fxControllerFactory, transitionManagerFactory, null);
    }

    public FXNavHost(FXControllerFactory fxControllerFactory,
                     TransitionManagerFactory transitionManagerFactory,
                     Stage stage) {

        this.fxControllerFactory = fxControllerFactory;
        this.transitionManagerFactory = transitionManagerFactory;
        this.stage = stage;
    }

    public void start() {

        if (stage == null)
            throw new NullPointerException("Cannot start because stage is null.");

        login("");
        stage.setMaximized(true);
        stage.show();
    }

    public Stage getStage() {

        return stage;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    private void login(String username) {

        final TransitionManager<Credentials> mainMenuTransitionManager = transitionManagerFactory
                .loginToMainMenu(_ -> System.out.println("NOOP"));

        final FXController controller = fxControllerFactory.login(
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

        final TransitionManager<RegistrationForm> mainMenuTransitionManager = transitionManagerFactory
                .registrationToMainMenu(_ -> System.out.println("NOOP"));

        final FXController controller = fxControllerFactory.registration(
                mainMenuTransitionManager,
                this::login,
                username
        );

        final Parent parent = controller.load();

        stage.getScene().setRoot(parent);
    }
}
