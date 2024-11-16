package de.code.junction.feldberger.mgmt.presentation.components.service;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;

public class ServiceFactory {

    private final PersistenceManager persistenceManager;

    public ServiceFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }
}
