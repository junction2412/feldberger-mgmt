package de.code.junction.feldberger.mgmt.data.access;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.document.Document;
import de.code.junction.feldberger.mgmt.data.access.document.DocumentDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.hibernate.*;
import de.code.junction.feldberger.mgmt.data.access.preference.Preference;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.data.access.transaction.TransactionDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PersistenceManager {

    private static PersistenceManager instance;

    private final SessionFactory sessionFactory;

    private PersistenceManager() {

        this.sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Transaction.class)
                .addAnnotatedClass(Document.class)
                .addAnnotatedClass(Preference.class)
                .buildSessionFactory();
    }

    public static PersistenceManager getInstance() {

        if (instance == null)
            instance = new PersistenceManager();

        return instance;
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

    public DocumentDataAccessObject documentDao() {

        return new HibernateDocumentDao(sessionFactory);
    }

    public PreferenceDataAccessObject preferenceDao() {

        return new HibernatePreferenceDao(sessionFactory);
    }

    public void shutdown() {

        sessionFactory.close();
    }
}
