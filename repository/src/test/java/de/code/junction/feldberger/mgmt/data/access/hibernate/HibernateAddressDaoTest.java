package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateAddressDaoTest {

    AddressDataAccessObject addressDao;
    SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Address.class)
                .buildSessionFactory();

        addressDao = new HibernateAddressDao(sessionFactory);
    }

    @AfterEach
    void tearDown() {

        sessionFactory.close();
    }

    @Test
    void cantFindUniqueAddressWhenEmpty() {

        final Optional<Address> uniqueAddress = addressDao.findUniqueAddress("test", "test",
                "test", "test", "test", "test");

        assertTrue(uniqueAddress.isEmpty());
    }

    @Test
    void cantFindByIDWhenEmpty() {

        final Optional<Address> optionalAddress = addressDao.findByID(2);

        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void cantGetAllWhenEmpty() {

        final List<Address> addresses = addressDao.getAll();

        assertTrue(addresses.isEmpty());
    }

    @Test
    void persistAddress() {

        final Address address = new Address();

        addressDao.persistAddress(address);

        assertNotEquals(0, address.getID());
    }

    @Test
    void persistAddressFailsIfAlreadyExists() {

        final Address address = new Address();

        addressDao.persistAddress(address);
        address.setID(0);

        final Executable whenPersistExisting = () -> addressDao.persistAddress(address);

        assertThrows(ConstraintViolationException.class, whenPersistExisting);
    }
}