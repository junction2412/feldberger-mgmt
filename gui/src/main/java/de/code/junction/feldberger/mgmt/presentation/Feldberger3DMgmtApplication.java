package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactoryProvider;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import javafx.application.Application;
import javafx.stage.Stage;

public class Feldberger3DMgmtApplication extends Application {

    private PersistenceManager persistenceManager;

    private FXMessenger messenger;
    private ApplicationNavContext navContext;

    @Override
    public void init() {

        System.out.println("Application init");

        persistenceManager = PersistenceManager.getInstance();
        messenger = new FXMessenger();

        final var transitionFactoryProvider = new TransitionFactoryProvider(
                persistenceManager,
                messenger
        );

        final var controllerFactory = new ApplicationControllerFactory(persistenceManager);

        navContext = new ApplicationNavContext(
                controllerFactory,
                transitionFactoryProvider
        );
    }

    @Override
    public void start(Stage stage) {

        System.out.println("Application start");

        messenger.setStage(stage);
        navContext.setScope(stage);

        navContext.navigateTo(new ApplicationNavRoute.LoginForm(""));

        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() {

        System.out.println("Application stop");

        persistenceManager.shutdown();
    }
}