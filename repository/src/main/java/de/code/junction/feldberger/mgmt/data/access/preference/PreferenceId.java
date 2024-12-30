package de.code.junction.feldberger.mgmt.data.access.preference;


import de.code.junction.feldberger.mgmt.data.access.user.User;

public record PreferenceId(User user, String scope, String name) {
}
