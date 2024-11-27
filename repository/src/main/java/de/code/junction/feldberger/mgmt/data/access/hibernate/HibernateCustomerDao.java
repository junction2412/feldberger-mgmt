package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer_;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;
import java.util.Optional;

public class HibernateCustomerDao
        extends HibernateDao
        implements CustomerDataAccessObject {

    public HibernateCustomerDao(SessionFactory sessionFactory) {

        super(sessionFactory);
    }

    @Override
    public List<Customer> getArchivedCustomers() {

        final var query = new CriteriaDefinition<>(sessionFactory, Customer.class) {
            {
                final JpaRoot<Customer> customers = from(Customer.class);

                where(isTrue(customers.get(Customer_.archived)));
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public List<Customer> getActiveCustomers() {

        final var query = new CriteriaDefinition<>(sessionFactory, Customer.class) {
            {
                final JpaRoot<Customer> customers = from(Customer.class);

                where(isFalse(customers.get(Customer_.archived)));
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public void persistCustomer(Customer customer) {

        sessionFactory.inTransaction(session -> session.persist(customer));
    }

    @Override
    public Optional<Customer> findByID(Integer id) {

        return Optional.ofNullable(sessionFactory.fromSession(session -> session.find(Customer.class, id)));
    }

    @Override
    public List<Customer> getAll() {

        final var query = new CriteriaDefinition<>(sessionFactory, Customer.class) {
            {
                from(Customer.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public List<Customer> getByNameOrCompanyName(String nameOrCompanyName) {

        final String pattern = '*' + nameOrCompanyName + '*';

        final var query = new CriteriaDefinition<>(sessionFactory, Customer.class) {
            {
                final var customers = from(Customer.class);

                where(
                        or(
                                like(
                                        concat(
                                                customers.get(Customer_.firstName),
                                                customers.get(Customer_.lastName)
                                        ),
                                        pattern
                                ),
                                like(customers.get(Customer_.companyName), pattern)
                        )
                );
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }
}
