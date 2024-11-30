package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateCustomerDaoTest {

    CustomerDataAccessObject customerDao;
    SessionFactory sessionFactory;
    AddressDataAccessObject addressDao;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Customer.class)
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

        final List<Customer> archivedCustomers = customerDao.getArchivedCustomers();

        assertTrue(archivedCustomers.isEmpty());
    }

    @Test
    void cantGetActiveCustomersWhenEmpty() {

        final List<Customer> activeCustomers = customerDao.getActiveCustomers();

        assertTrue(activeCustomers.isEmpty());
    }

    @Test
    void persistCustomerFailsMiserably() {

        final Customer customer = new Customer("12345", "Biden", "Joe", "U:S:A", "joe@biden.com", "0815", "0816",
                new Address());

        final Executable executable = () -> customerDao.persistCustomer(customer);

        assertThrows(Exception.class, executable);
    }

    @Test
    void persistCustomerNoLongerFailsMiserably() {

        final Address address = new Address();
        addressDao.persistAddress(address);

        final Customer customer = new Customer("12345", "Biden", "Joe", "U:S:A", "joe@biden.com", "0815", "0816",
                address);

        final Executable executable = () -> customerDao.persistCustomer(customer);

        assertDoesNotThrow(executable);
    }

    @Test
    void cantFindByIdWhenEmpty() {

        final Optional<Customer> optionalCustomer = customerDao.findById(2);

        assertTrue(optionalCustomer.isEmpty());
    }

    @Test
    void findById_AlsoLoadsAddress() {

        final Address address = new Address();
        addressDao.persistAddress(address);

        final Customer customer = new Customer("12345", "Biden", "Joe", "U:S:A", "joe@biden.com", "0815", "0816",
                address);

        customerDao.persistCustomer(customer);

        final Integer customerID = customer.getId();

        final Optional<Customer> optionalCustomer = customerDao.findById(customerID);
        assertTrue(optionalCustomer.isPresent());

        final Customer customerFromDb = optionalCustomer.get();
        assertNotNull(customerFromDb.getAddress());
    }

    @Test
    void cantGetAllWhenEmpty() {

        final List<Customer> customers = customerDao.getAll();

        assertTrue(customers.isEmpty());
    }
}