package de.code.junction.feldberger.mgmt.presentation.components.common;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;

public abstract class TransitionFactory {

    /**
     * A {@link PersistenceManager} that is used to inject necessary {@link DataAccessObject}s.
     */
    protected final PersistenceManager persistenceManager;

    /**
     * The {@link Messenger} that is injected.
     */
    protected final Messenger messenger;

    protected TransitionFactory(PersistenceManager persistenceManager, Messenger messenger) {

        this.persistenceManager = persistenceManager;
        this.messenger = messenger;
    }
}
