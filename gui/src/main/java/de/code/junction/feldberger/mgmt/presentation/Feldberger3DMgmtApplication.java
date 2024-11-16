package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXNavHost;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManagerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.service.ServiceFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Feldberger3DMgmtApplication extends Application {

    private static final PersistenceManager persistenceManager = PersistenceManager.INSTANCE;

    private FXControllerFactory fxControllerFactory;
    private TransitionManagerFactory transitionManagerFactory;

    @Override
    public void init() throws Exception {

        System.out.println("Application init");

        fxControllerFactory = new FXControllerFactory(new ServiceFactory(persistenceManager));
        transitionManagerFactory = new TransitionManagerFactory(persistenceManager);
    }

    @Override
    public void start(Stage stage) throws IOException {

        System.out.println("Application start");

        final FXNavHost navHost = new FXNavHost(fxControllerFactory, transitionManagerFactory, stage);

        final FXMessenger messenger = new FXMessenger(stage);
        transitionManagerFactory.setMessenger(messenger);

        navHost.start();
    }

    @Override
    public void stop() throws Exception {

        System.out.println("Application stop");

        persistenceManager.shutdown();
    }
}