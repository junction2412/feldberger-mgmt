package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUserDaoTest {

    UserDataAccessObject userDao;
    SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        userDao = new HibernateUserDao(sessionFactory);
    }

    @AfterEach
    void tearDown() {

        sessionFactory.close();
    }

    @Test
    void getAllWithEmptyTableReturnsEmptyList() {
        // given: empty table

        // when: all records are retrieved
        final List<User> all = userDao.getAll();

        // then: the list is empty
        assertTrue(all.isEmpty());
    }

    @Test
    void getByUsernameWithEmptyTableReturnsEmptyOptional() {
        // given: username, empty table
        var username = "username";

        // when: user is queried
        final Optional<User> user = userDao.findByUsername(username, false);

        // then: optional is empty
        assertTrue(user.isEmpty());
    }

    @Test
    void persistUserInsertsNewUser() {
        // given: new user
        final User user = new User("username", "my_hash", "some salt");
        final Integer id = user.getID();

        // when: user is persisted
        userDao.persistUser(user);

        // then: user has received an id
        assertNotEquals(id, user.getID());
    }

    @Test
    void findByIDWithPersistedUserReturnsPresentOptional() {
        // given: persisted user
        final User user = new User("username", "my_hash", "some salt");
        userDao.persistUser(user);

        // when: user is retrieved by id
        final Optional<User> optionalUser = userDao.findByID(user.getID());

        // then: optional is present
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void getByUsernameWithCorrectNameReturnsPresentOptional() {
        // given: username
        final String username = "username";
        userDao.persistUser(new User(username, "my_hash", "some salt"));

        // when: user is queried
        final Optional<User> optionalUser = userDao.findByUsername(username);

        // then: optional is present
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void getByUsernameWithCorrectNameAndInactiveReturnsEmptyOptional() {
        // given: username
        final String username = "username";
        userDao.persistUser(new User(username, "my_hash", "some salt"));

        // when: user is queried
        final Optional<User> optionalUser = userDao.findByUsername(username, false);

        // then: optional is empty
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void getAllActiveUsersWithEmptyTableReturnsEmptyList() {
        // given: empty table

        // when: all active records are retrieved
        final List<User> all = userDao.getAllActiveUsers();

        // then: the list is empty
        assertTrue(all.isEmpty());
    }

    @Test
    void getAllActiveUsersWithFilledTableReturnsFilledList() {
        // given: filled table
        userDao.persistUser(new User("username", "hash", "salt"));

        // when: all active records are retrieved
        final List<User> all = userDao.getAllActiveUsers();

        // then: the list is filled
        assertFalse(all.isEmpty());
    }

    @Test
    void getAllActiveUsersWithFilledTableWithInactiveUsersReturnsEmptyList() {
        // given: filled table
        userDao.persistUser(new User());

        // when: all active records are retrieved
        final List<User> all = userDao.getAllActiveUsers();

        // then: the list is empty
        assertTrue(all.isEmpty());
    }
}