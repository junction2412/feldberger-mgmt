package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.ServiceFactory;
import de.code.junction.feldberger.mgmt.presentation.components.ViewFactory;
import de.code.junction.feldberger.mgmt.presentation.components.ViewModelFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavigator;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.Registration;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import javafx.application.Application;
import javafx.stage.Stage;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.Login;

public class Feldberger3DMgmtApplication extends Application {

    private PersistenceManager persistenceManager;


    @Override
    public void init() {
        persistenceManager = PersistenceManager.getInstance();
    }

    @Override
    public void start(Stage stage) {

        final var viewModelFactory = new ViewModelFactory(
                new FXMessenger(stage),
                new ServiceFactory(persistenceManager)
        );

        final var viewFactory = new ViewFactory(viewModelFactory);
        final var navigator = new ApplicationNavigator(stage, viewFactory);

        final var NO_USERS_REGISTERED = persistenceManager.userDao().countAll() == 0;
        final var entryPoint = NO_USERS_REGISTERED
                ? new Registration()
                : new Login();

        navigator.navigateTo(entryPoint);

        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() {
        persistenceManager.shutdown();
    }
}