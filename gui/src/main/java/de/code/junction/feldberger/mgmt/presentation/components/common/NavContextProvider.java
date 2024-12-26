package de.code.junction.feldberger.mgmt.presentation.components.common;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.cache.Cache;
import de.code.junction.feldberger.mgmt.presentation.cache.ScopeName;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.Stack;

public class NavContextProvider {

    private static NavContextProvider instance;

    private final TransitionFactoryProvider transitionFactoryProvider;
    private final PersistenceManager persistenceManager;

    private ApplicationNavContext applicationNavContext;
    private MainMenuNavContext mainMenuNavContext;

    private NavContextProvider(PersistenceManager persistenceManager, Messenger messenger) {

        this.persistenceManager = persistenceManager;
        transitionFactoryProvider = new TransitionFactoryProvider(persistenceManager, messenger);
    }

    public static NavContextProvider getInstance(Messenger messenger) {

        if (instance == null)
            instance = new NavContextProvider(PersistenceManager.getInstance(), messenger);

        return instance;
    }

    public static NavContextProvider getInstance() {

        return Objects.requireNonNull(instance);
    }

    public ApplicationNavContext application() {

        if (applicationNavContext == null)
            applicationNavContext = new ApplicationNavContext(
                    new Stack<>(),
                    new ApplicationControllerFactory(persistenceManager),
                    transitionFactoryProvider.applicationTransitionFactory()
            );

        return applicationNavContext;
    }

    public void initMainMenu(Pane scope, int userId) {

        final var routes = Cache.<MainMenuRoute>getScopeRoutes(userId, ScopeName.MAIN_MENU);
        final var navContext = new MainMenuNavContext(
                routes,
                transitionFactoryProvider.mainMenuTransitionFactory(),
                new MainMenuControllerFactory(persistenceManager)
        );

        navContext.setScope(scope);

        // always overwrite mainMenuNavContext whenever a new one is created
        // to only keep track of the recent navigation stack being cached
        mainMenuNavContext = navContext;

        if (!routes.isEmpty())
            navContext.push(routes.peek());
    }

    public MainMenuNavContext mainMenu(int userId) {

        final var navContext = new MainMenuNavContext(
                Cache.getScopeRoutes(userId, ScopeName.MAIN_MENU),
                transitionFactoryProvider.mainMenuTransitionFactory(),
                new MainMenuControllerFactory(persistenceManager)
        );

        // always overwrite mainMenuNavContext whenever a new one is created
        // to only keep track of the recent navigation stack being cached
        mainMenuNavContext = navContext;

        return navContext;
    }
}
