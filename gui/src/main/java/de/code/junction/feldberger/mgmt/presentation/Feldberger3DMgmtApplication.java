package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXNavHost;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.components.service.ServiceFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Feldberger3DMgmtApplication extends Application {

    private PersistenceManager persistenceManager;

    private FXMessenger messenger;
    private FXNavHost navHost;

    @Override
    public void init() throws Exception {

        System.out.println("Application init");

        persistenceManager = PersistenceManager.INSTANCE;

        messenger = new FXMessenger();

        FXControllerFactory fxControllerFactory = new FXControllerFactory(new ServiceFactory(persistenceManager));
        TransitionFactory transitionFactory = new TransitionFactory(persistenceManager, messenger);

        navHost = new FXNavHost(fxControllerFactory, transitionFactory);
    }

    @Override
    public void start(Stage stage) throws IOException {

        System.out.println("Application start");

        messenger.setStage(stage);
        navHost.setStage(stage);

        navHost.start();
    }

    @Override
    public void stop() throws Exception {

        System.out.println("Application stop");

        persistenceManager.shutdown();
    }
}