package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.MessageType;

import java.text.MessageFormat;

import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.salt;
import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getMessageStringResources;

public class PerformRegistration {

    private final UserDataAccessObject userDao;

    public PerformRegistration(UserDataAccessObject userDao) {

        this.userDao = userDao;
    }

    public RegistrationResult submit(String username, String password, String repeatedPassword) {

        final var arePasswordInputsEqual = password.equals(repeatedPassword);
        final var isPasswordMinimumLength = 7 < password.length();
        final var isPasswordNotMaxLength = password.length() < 65;
        final var isUsernameNotEmpty = !username.isEmpty();
        final var isUsernameNotTaken = userDao.findByUsername(username).isEmpty();

        final var reasons = new StringBuilder();
        final var bundle = getMessageStringResources();

        if (!arePasswordInputsEqual)
            reasons.append("\n").append(bundle.getString("registration.failed.content.reason.password.inputs.not.equal"));

        if (!isPasswordMinimumLength)
            reasons.append("\n").append(bundle.getString("registration.failed.content.reason.password.too.short"));

        if (!isPasswordNotMaxLength)
            reasons.append("\n").append(bundle.getString("registration.failed.content.reason.password.too.long"));

        if (!isUsernameNotEmpty)
            reasons.append("\n").append(bundle.getString("registration.failed.content.reason.username.empty"));

        if (!isUsernameNotTaken)
            reasons.append("\n").append(bundle.getString("registration.failed.content.reason.username.taken"));

        final var isPasswordValid = arePasswordInputsEqual && isPasswordMinimumLength && isPasswordNotMaxLength;
        final var isUsernameValid = isUsernameNotEmpty && isUsernameNotTaken;

        if (!(isPasswordValid && isUsernameValid)) {
            final var title = bundle.getString("registration.failed.title");
            final var header = bundle.getString("registration.failed.header");
            final var content = MessageFormat.format(bundle.getString("registration.failed.content.format"),
                    reasons);

            return new RegistrationResult.Failure(new Message(MessageType.WARNING, title, header, content));
        }

        final var passwordSalt = salt();
        final var passwordHash = hashPassword(password, passwordSalt);
        final var user = new User(username, passwordHash, passwordSalt);

        final var AT_LEAST_ONE_USER_EXISTS = 0 < userDao.countAll();

        if (AT_LEAST_ONE_USER_EXISTS) {
            user.setInactive();
        }

        userDao.persistUser(user);

        return new RegistrationResult.Success(user);
    }
}
