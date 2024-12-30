package de.code.junction.feldberger.mgmt.data.access.preference;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.user.User;

import java.util.List;

public interface PreferenceDataAccessObject extends DataAccessObject<PreferenceId, Preference> {

    /**
     * Retrieve all user-specific preferences.
     *
     * @param user user id
     * @return preferences of the user
     */
    List<Preference> getAllByUser(User user);

    /**
     * Insert or update preference
     *
     * @param preference preference to be persisted
     */
    void persistPreference(Preference preference);

    /**
     * Delete all preferences of the specified user and scope.
     *
     * @param user  user id
     * @param scope scope of the preferences to be deleted
     * @return amount of deleted records
     */
    int deleteForUserAndScope(User user, String scope);

    /**
     * Delete a single preference by its id.
     *
     * @param preference preference id
     */
    void delete(Preference preference);
}
