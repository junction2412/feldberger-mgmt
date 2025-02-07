package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.ServiceFactory;
import de.code.junction.feldberger.mgmt.presentation.components.ViewFactory;
import de.code.junction.feldberger.mgmt.presentation.components.ViewModelFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.Registration;
import de.code.junction.feldberger.mgmt.presentation.components.common.NavigatorFactory;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import javafx.application.Application;
import javafx.stage.Stage;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.Login;

public class Feldberger3DMgmtApplication extends Application {

    private PersistenceManager persistenceManager;


    @Override
    public void init() {

        System.out.println("Application init");
        persistenceManager = PersistenceManager.getInstance();
    }

    @Override
    public void start(Stage stage) {

        System.out.println("Application start");

        final var messenger = new FXMessenger();
        messenger.setStage(stage);

        final var navigatorFactory = NavigatorFactory.getInstance(messenger);
        final var viewModelFactory = new ViewModelFactory(messenger, navigatorFactory, new ServiceFactory(persistenceManager));
        final var viewFactory = new ViewFactory(viewModelFactory);
        final var navigator = navigatorFactory.application(viewFactory);

        navigator.setScope(stage);

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

        System.out.println("Application stop");
        persistenceManager.shutdown();
    }
}