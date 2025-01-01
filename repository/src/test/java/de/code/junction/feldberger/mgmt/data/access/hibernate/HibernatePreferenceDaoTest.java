package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.preference.Preference;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.preference.PreferenceId;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HibernatePreferenceDaoTest {

    private static PersistenceManager persistenceManager;
    private PreferenceDataAccessObject preferenceDao;
    private UserDataAccessObject userDao;

    @BeforeAll
    static void init() {

        persistenceManager = PersistenceManager.getInstance();
    }

    @BeforeEach
    void setUp() {

        preferenceDao = persistenceManager.preferenceDao();
        userDao = persistenceManager.userDao();
    }

    @AfterEach
    void tearDown() {

        preferenceDao.getAll().stream()
                .map(Preference::getId)
                .forEach(preferenceDao::delete);
    }

    @AfterAll
    static void shutdown() {

        PersistenceManager.getInstance().shutdown();
    }


    @Test
    void findByIdWithEmptyTableReturnsEmptyRecord() {
        // given: absent preference identity
        final var user = new User("jdoe", "hash", "salt");
        userDao.persistUser(user);

        final var id = new PreferenceId(user, "test", "preference.test");

        // when: absent record is retrieved
        final var optionalPreference = preferenceDao.findById(id);

        // then: record is empty
        assertTrue(optionalPreference.isEmpty());
    }

    @Test
    void getAllWithEmptyTableReturnsEmptyList() {
        // given: an empty table
        // when: all preferences are retrieved
        final var preferences = preferenceDao.getAll();

        // then: list is empty
        assertTrue(preferences.isEmpty());
    }

    @Test
    void getAllByUserWithEmptyTableReturnsEmptyList() {
        // given: empty table, existing user
        final var user = new User("jdoe2", "hash", "salt");
        userDao.persistUser(user);

        // when: user preferences are retrieved
        final var preferences = preferenceDao.getAllByUser(user);

        // then: list is empty
        assertTrue(preferences.isEmpty());
    }

    @Test
    void persistPreference() {
        // given: existing user, new preference
        final var user = new User("jdoe3", "hash", "salt");
        userDao.persistUser(user);

        final var preference = new Preference(user, "test", "preference.test", "{\"value\":0}");

        // when: preference is persisted
        preferenceDao.persistPreference(preference);

        // then: preference exists
        final var optionalPreference = preferenceDao.findById(preference.getId());
        assertTrue(optionalPreference.isPresent());
    }

    @Test
    void deleteForUserAndScope() {
        // given: user, multiple preferences of same scope
        final var user = new User("jdoe4", "hash", "salt");
        userDao.persistUser(user);

        preferenceDao.persistPreference(new Preference(user, "test", "pref1.test", null));
        preferenceDao.persistPreference(new Preference(user, "test", "pref2.test", null));

        // when: all by user and scope are deleted
        final int deleted = preferenceDao.deleteForUserAndScope(user, "test");

        // then: they are deleted
        assertEquals(2, deleted);
        assertTrue(preferenceDao.getAllByUser(user).isEmpty());
    }

    @Test
    void delete() {
        // works. see #tearDown
    }
}