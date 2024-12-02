package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.data.access.transaction.TransactionDataAccessObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class HibernateTransactionDao
        extends HibernateDao
        implements TransactionDataAccessObject {

    public HibernateTransactionDao(SessionFactory sessionFactory) {

        super(sessionFactory);
    }

    @Override
    public Optional<Transaction> findById(Integer id) {

        final Transaction transaction = sessionFactory.fromSession(session -> session.find(Transaction.class, id));

        return Optional.ofNullable(transaction);
    }

    @Override
    public List<Transaction> getAll() {

        final var query = new CriteriaDefinition<>(sessionFactory, Transaction.class) {
            {
                from(Transaction.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public void persistTransaction(Transaction transaction) {

        final Consumer<Session> sessionConsumer = (transaction.getId() == 0)
                ? session -> session.persist(transaction)
                : session -> session.merge(transaction);

        sessionFactory.inTransaction(sessionConsumer);
    }
}
