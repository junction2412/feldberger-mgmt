package de.code.junction.feldberger.mgmt.presentation.components.common;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.cache.Cache;
import de.code.junction.feldberger.mgmt.presentation.cache.ScopeName;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavContext;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;

import java.util.Objects;
import java.util.Stack;

public class NavContextProvider {

    private static NavContextProvider instance;

    private final TransitionFactoryProvider transitionFactoryProvider;
    private final PersistenceManager persistenceManager;

    private ApplicationNavContext applicationNavContext;

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

    public MainMenuNavContext mainMenu(int userId) {

        return new MainMenuNavContext(
                Cache.getScopeRoutes(
                        userId,
                        ScopeName.MAIN_MENU
                ),
                transitionFactoryProvider.mainMenuTransitionFactory(),
                new MainMenuControllerFactory(persistenceManager)
        );
    }
}
