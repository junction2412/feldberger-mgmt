package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.document.Document;
import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class HibernateCustomerDaoTest {

    CustomerDataAccessObject customerDao;
    SessionFactory sessionFactory;
    AddressDataAccessObject addressDao;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Transaction.class)
                .addAnnotatedClass(Document.class)
                .buildSessionFactory();

        customerDao = new HibernateCustomerDao(sessionFactory);
        addressDao = new HibernateAddressDao(sessionFactory);
    }

    @AfterEach
    void tearDown() {

        sessionFactory.close();
    }

    @Test
    void cantGetArchivedCustomersWhenEmpty() {

        final var archivedCustomers = customerDao.getArchivedCustomers();

        assertTrue(archivedCustomers.isEmpty());
    }

    @Test
    void cantGetActiveCustomersWhenEmpty() {

        final var activeCustomers = customerDao.getActiveCustomers();

        assertTrue(activeCustomers.isEmpty());
    }

    @Test
    void persistCustomerFailsMiserably() {

        final var customer = new Customer(
                "12345",
                "Biden",
                "Joe",
                "U:S:A",
                "joe@biden.com",
                "0815",
                "0816",
                new Address()
        );

        final Executable executable = () -> customerDao.persistCustomer(customer);

        assertThrows(Exception.class, executable);
    }

    @Test
    void persistCustomerNoLongerFailsMiserably() {

        final var address = new Address();
        addressDao.persistAddress(address);

        final var customer = new Customer(
                "12345",
                "Biden",
                "Joe",
                "U:S:A",
                "joe@biden.com",
                "0815",
                "0816",
                address
        );

        final Executable executable = () -> customerDao.persistCustomer(customer);

        assertDoesNotThrow(executable);
    }

    @Test
    void cantFindByIdWhenEmpty() {

        final var optionalCustomer = customerDao.findById(2);

        assertTrue(optionalCustomer.isEmpty());
    }

    @Test
    void findByIdAlsoLoadsAddress() {

        final var address = new Address();
        addressDao.persistAddress(address);

        final var customer = new Customer(
                "12345",
                "Biden",
                "Joe",
                "U:S:A",
                "joe@biden.com",
                "0815",
                "0816",
                address
        );

        customerDao.persistCustomer(customer);

        final var customerID = customer.getId();

        final var optionalCustomer = customerDao.findById(customerID);
        assertTrue(optionalCustomer.isPresent());

        final var customerFromDb = optionalCustomer.get();
        assertNotNull(customerFromDb.getAddress());
    }

    @Test
    void cantGetAllWhenEmpty() {

        final var customers = customerDao.getAll();

        assertTrue(customers.isEmpty());
    }
}