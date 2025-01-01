package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.preference.Preference;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceId;
import de.code.junction.feldberger.mgmt.data.access.preference.Preference_;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernatePreferenceDao
        extends HibernateDao
        implements PreferenceDataAccessObject {

    public HibernatePreferenceDao(SessionFactory sessionFactory) {

        super(sessionFactory);
    }

    @Override
    public Optional<Preference> findById(PreferenceId id) {

        return Optional.ofNullable(sessionFactory.fromSession(session -> session.find(Preference.class, id)));
    }

    @Override
    public List<Preference> getAll() {

        final var query = new CriteriaDefinition<>(sessionFactory, Preference.class) {
            {
                from(Preference.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createQuery(query).list());
    }

    @Override
    public List<Preference> getAllByUser(User user) {

        final var query = new CriteriaDefinition<>(sessionFactory, Preference.class) {
            {
                final var preferences = from(Preference.class);
                where(preferences.get(Preference_.user).equalTo(user));
            }
        };

        return sessionFactory.fromSession(session -> session.createQuery(query).list());
    }

    @Override
    public void persistPreference(Preference preference) {

        final Function<PreferenceId, Consumer<Session>> factory = id -> {

            if (findById(id).isPresent())
                return session -> {
                    final var reference = session.getReference(preference.getUser());
                    final var referencedId = new PreferenceId(reference, id.scope(), id.name());
                    preference.setId(referencedId);

                    session.merge(preference);
                };
            else
                return session -> {
                    final var reference = session.getReference(preference.getUser());
                    final var referencedId = new PreferenceId(reference, id.scope(), id.name());
                    preference.setId(referencedId);

                    session.persist(preference);
                };
        };

        final var withSession = factory.apply(preference.getId());

        sessionFactory.inTransaction(withSession);
    }

    @Override
    public int deleteForUserAndScope(User user, String scope) {

        return sessionFactory.fromTransaction(session -> {

            final var builder = session.getCriteriaBuilder();
            final var delete = builder.createCriteriaDelete(Preference.class);
            final var preferences = delete.from(Preference.class);

            delete.where(
                    builder.and(
                            preferences.get(Preference_.user).equalTo(user),
                            preferences.get(Preference_.scope).equalTo(scope)
                    )
            );

            return session.createMutationQuery(delete).executeUpdate();
        });
    }

    @Override
    public void delete(PreferenceId id) {

        sessionFactory.inTransaction(session -> {

            final var builder = session.getCriteriaBuilder();
            final var delete = builder.createCriteriaDelete(Preference.class);
            final var preferences = delete.from(Preference.class);

            delete.where(
                    builder.and(
                            preferences.get(Preference_.user).equalTo(id.user()),
                            preferences.get(Preference_.scope).equalTo(id.scope()),
                            preferences.get(Preference_.name).equalTo(id.name())
                    )
            );

            session.createMutationQuery(delete).executeUpdate();
        });
    }
}
