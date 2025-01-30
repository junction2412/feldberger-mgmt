package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;

public sealed interface RegistrationResult {

    record Success(User user) implements RegistrationResult {
    }

    record Failure(Message message) implements RegistrationResult {
    }
}
