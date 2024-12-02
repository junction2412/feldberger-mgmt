package de.code.junction.feldberger.mgmt.data.access;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.hibernate.HibernateAddressDao;
import de.code.junction.feldberger.mgmt.data.access.hibernate.HibernateCustomerDao;
import de.code.junction.feldberger.mgmt.data.access.hibernate.HibernateTransactionDao;
import de.code.junction.feldberger.mgmt.data.access.hibernate.HibernateUserDao;
import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.data.access.transaction.TransactionDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PersistenceManager {

    public static final PersistenceManager INSTANCE = new PersistenceManager();

    private final SessionFactory sessionFactory;

    private PersistenceManager() {

        this.sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Transaction.class)
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public UserDataAccessObject userDao() {

        return new HibernateUserDao(sessionFactory);
    }

    public AddressDataAccessObject addressDao() {

        return new HibernateAddressDao(sessionFactory);
    }

    public CustomerDataAccessObject customerDao() {

        return new HibernateCustomerDao(sessionFactory);
    }

    public CustomerService customerService() {

        return new CustomerService(customerDao(), addressDao());
    }

    public TransactionDataAccessObject transactionDao() {

        return new HibernateTransactionDao(sessionFactory);
    }

    public void shutdown() {

        sessionFactory.close();
    }
}
