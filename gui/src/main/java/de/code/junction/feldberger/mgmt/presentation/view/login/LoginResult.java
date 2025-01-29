package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;

public sealed interface LoginResult {

    record Failure(Message message) implements LoginResult {
    }

    record Success(User user) implements LoginResult {
    }
}
