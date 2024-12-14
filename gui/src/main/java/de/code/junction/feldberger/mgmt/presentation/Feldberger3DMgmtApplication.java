package de.code.junction.feldberger.mgmt.presentation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.components.common.NavContextProvider;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.FXMessenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;

public class Feldberger3DMgmtApplication extends Application {

    @Override
    public void init() {

        System.out.println("Application init");

        PersistenceManager.getInstance();
    }

    @Override
    public void start(Stage stage) {

        System.out.println("Application start");

        final var messenger = new FXMessenger();
        messenger.setStage(stage);

        final var navContext = NavContextProvider.getInstance(messenger).application();

        navContext.setScope(stage);
        navContext.push(new Route<>(ApplicationRoute.LOGIN, new HashMap<>()));

        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() {

        System.out.println("Application stop");

        PersistenceManager.getInstance().shutdown();
    }
}