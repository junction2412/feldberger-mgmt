package de.code.junction.feldberger.mgmt.data.access;

import de.code.junction.feldberger.mgmt.data.access.hibernate.HibernateUserDao;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PersistenceManager {

    public static final PersistenceManager INSTANCE = new PersistenceManager();

    private final SessionFactory sessionFactory;

    private PersistenceManager() {

        this.sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public UserDataAccessObject userDao() {

        return new HibernateUserDao(sessionFactory);
    }

    public void shutdown() {

        sessionFactory.close();
    }
}
