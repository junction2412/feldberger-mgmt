package de.code.junction.feldberger.mgmt.presentation.components.common;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationTransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuTransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;

public class TransitionFactoryProvider {

    private final PersistenceManager persistenceManager;
    private final Messenger messenger;

    public TransitionFactoryProvider(PersistenceManager persistenceManager, Messenger messenger) {
        this.persistenceManager = persistenceManager;
        this.messenger = messenger;
    }

    public ApplicationTransitionFactory applicationTransitionFactory() {

        return new ApplicationTransitionFactory(persistenceManager, messenger);
    }

    public MainMenuTransitionFactory mainMenuTransitionFactory() {

        return new MainMenuTransitionFactory(persistenceManager, messenger);
    }
}
