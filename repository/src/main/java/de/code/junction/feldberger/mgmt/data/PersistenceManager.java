package de.code.junction.feldberger.mgmt.data;

import org.hibernate.SessionFactory;

public class PersistenceManager {

    private static PersistenceManager instance;

    private PersistenceManager(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    private final SessionFactory sessionFactory;

    public static PersistenceManager initialize(SessionFactory sessionFactory) {

        if (instance == null)
            instance = new PersistenceManager(sessionFactory);

        return instance;
    }

    public static PersistenceManager getInstance() {

        return instance;
    }

    public SessionFactory getSessionFactory() {

        return sessionFactory;
    }
}
