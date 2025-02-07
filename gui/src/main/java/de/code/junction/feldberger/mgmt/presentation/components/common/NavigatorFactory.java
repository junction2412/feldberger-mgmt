package de.code.junction.feldberger.mgmt.presentation.components.common;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.ViewFactory;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavigator;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;

import java.util.Objects;

public class NavigatorFactory {

    private static NavigatorFactory instance;

    private final TransitionFactoryProvider transitionFactoryProvider;
    private final PersistenceManager persistenceManager;

    private ApplicationNavigator applicationNavigator;

    private NavigatorFactory(PersistenceManager persistenceManager, Messenger messenger) {

        this.persistenceManager = persistenceManager;
        transitionFactoryProvider = new TransitionFactoryProvider(persistenceManager, messenger);
    }

    public static NavigatorFactory getInstance(Messenger messenger) {

        if (instance == null)
            instance = new NavigatorFactory(PersistenceManager.getInstance(), messenger);

        return instance;
    }

    public static NavigatorFactory getInstance() {

        return Objects.requireNonNull(instance);
    }

    public ApplicationNavigator application(ViewFactory viewFactory) {

        if (applicationNavigator == null)
            applicationNavigator = new ApplicationNavigator(viewFactory);

        return applicationNavigator;
    }

    public ApplicationNavigator application() {
        return applicationNavigator;
    }
}
