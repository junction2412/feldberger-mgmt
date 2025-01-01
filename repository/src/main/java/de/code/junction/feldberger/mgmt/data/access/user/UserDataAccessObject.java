package de.code.junction.feldberger.mgmt.data.access.user;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;

import java.util.List;
import java.util.Optional;

/**
 * The {@link DataAccessObject} definition for {@link User}s.
 *
 * @author J. Murray
 */
public interface UserDataAccessObject extends DataAccessObject<Integer, User> {

    /**
     * Find a user by its username.
     *
     * @param username username
     * @param active   whether you expect the user to be active or not
     * @return optional user
     */
    Optional<User> findByUsername(String username, boolean active);

    /**
     * Fetch all active users.
     *
     * @return list of active users
     */
    List<User> getAllActiveUsers();

    /**
     * Insert or update a user.
     *
     * @param user user
     */
    void persistUser(User user);

    /**
     * Find an active user by its username.
     *
     * @param username username
     * @return optional active user
     */
    default Optional<User> findByUsername(String username) {

        return findByUsername(username, true);
    }

    long countAll();

    User getReference(int userId);
}
