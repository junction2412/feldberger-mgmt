package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationTransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import de.code.junction.feldberger.mgmt.presentation.components.service.ServiceFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Feldberger3DMgmtApplication extends Application {

    private PersistenceManager persistenceManager;

    private FXMessenger messenger;
    private ApplicationNavContext navContext;

    @Override
    public void init() throws Exception {

        System.out.println("Application init");

        persistenceManager = PersistenceManager.INSTANCE;

        messenger = new FXMessenger();

        ApplicationControllerFactory controllerFactory = new ApplicationControllerFactory(new ServiceFactory(persistenceManager));
        ApplicationTransitionFactory transitionFactory = new ApplicationTransitionFactory(persistenceManager, messenger);

        navContext = new ApplicationNavContext(controllerFactory, transitionFactory);
    }

    @Override
    public void start(Stage stage) throws IOException {

        System.out.println("Application start");

        messenger.setStage(stage);
        navContext.setStage(stage);

        navContext.navigateTo(new ApplicationNavRoute.LoginForm(""));

        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() throws Exception {

        System.out.println("Application stop");

        persistenceManager.shutdown();
    }
}