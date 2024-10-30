package de.code.junction.feldberger.mgmt.data.access.hibernate;

import org.hibernate.SessionFactory;

public abstract class HibernateDao {

    protected final SessionFactory sessionFactory;

    public HibernateDao(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }
}
