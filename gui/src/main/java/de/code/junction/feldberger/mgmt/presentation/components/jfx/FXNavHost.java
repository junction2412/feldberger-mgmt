package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManagerFactory;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
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

        final LoginController controller = fxControllerFactory.login(
                transitionManagerFactory.loginToMainMenu(_ -> System.out.println("Login attempt successful!")),
                _username -> System.out.println("Registering " + _username),
                username
        );

        stage.setScene(new Scene(controller.load()));
    }
}
