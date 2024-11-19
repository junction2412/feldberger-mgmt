package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.user.User_;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;
import java.util.Optional;

public class HibernateUserDao
        extends HibernateDao
        implements UserDataAccessObject {

    public HibernateUserDao(SessionFactory sessionFactory) {

        super(sessionFactory);
    }

    @Override
    public Optional<User> findByUsername(String username, boolean active) {

        final CriteriaDefinition<User> query = new CriteriaDefinition<>(sessionFactory, User.class) {
            {
                final JpaRoot<User> user = from(User.class);
                final JpaPredicate usernameEquals = equal(user.get("username"), username);
                final JpaPredicate isActive = equal(user.get("active"), active);

                where(and(usernameEquals, isActive));
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).stream().findFirst());
    }

    @Override
    public List<User> getAllActiveUsers() {

        final CriteriaDefinition<User> query = new CriteriaDefinition<>(sessionFactory, User.class) {
            {
                final JpaRoot<User> user = from(User.class);
                where(isTrue(user.get(User_.active)));
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public void persistUser(User user) {

        sessionFactory.inTransaction(session -> session.persist(user));
    }

    @Override
    public Optional<User> findByID(Integer id) {

        return sessionFactory.fromSession(session -> Optional.ofNullable(session.find(User.class, id)));
    }

    @Override
    public List<User> getAll() {

        final CriteriaDefinition<User> query = new CriteriaDefinition<>(sessionFactory, User.class) {
            {
                from(User.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public long countAll() {

        final var users = new CriteriaDefinition<>(sessionFactory, User.class) {
            {
                from(User.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(users).getResultCount());
    }
}
