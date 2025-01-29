package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;

import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;
import static de.code.junction.feldberger.mgmt.presentation.view.login.LoginResult.Failure;
import static de.code.junction.feldberger.mgmt.presentation.view.login.LoginResult.Success;

/// A single testable unit of login business logic.
///
/// @author J. Murray
public class PerformLogin {

    private final UserDataAccessObject userDao;

    public PerformLogin(UserDataAccessObject userDao) {

        this.userDao = userDao;
    }

    public LoginResult submit(String username, String password) {

        final var optionalUser = userDao.findByUsername(username);

        if (optionalUser.isEmpty())
            return new Failure(Messages.LOGIN_FAILED_WRONG_CREDENTIALS);

        final var user = optionalUser.get();

        final var passwordSalt = user.getPasswordSalt();
        final var hashedPasswordInput = hashPassword(password, passwordSalt);
        final var passwordCorrect = hashedPasswordInput.equals(user.getPasswordHash());

        if (!passwordCorrect)
            return new Failure(Messages.LOGIN_FAILED_WRONG_CREDENTIALS);

        return new Success(user);
    }
}
