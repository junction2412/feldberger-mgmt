package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.LoginForm;
import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.UserSession;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;

public class LoginMainMenuTransitionLifecycle implements TransitionLifecycle<LoginForm, UserSession> {

    private final Messenger messenger;
    private final UserDataAccessObject userDao;
    private final Consumer<UserSession> onEnd;

    public LoginMainMenuTransitionLifecycle(Messenger messenger,
                                            UserDataAccessObject userDao,
                                            Consumer<UserSession> onEnd) {

        this.messenger = messenger;
        this.userDao = userDao;
        this.onEnd = onEnd;
    }

    @Override
    public boolean validate(LoginForm loginForm) {

        final Optional<User> optionalUser = userDao.findByUsername(loginForm.username());

        if (optionalUser.isEmpty()) {

            messenger.send(Messages.LOGIN_FAILED_WRONG_CREDENTIALS);
            return false;
        }

        final User user = optionalUser.get();

        final String passwordSalt = user.getPasswordSalt();
        final String hashedPasswordInput = hashPassword(loginForm.password(), passwordSalt);

        final boolean passwordCorrect = hashedPasswordInput.equals(user.getPasswordHash());

        if (!passwordCorrect)
            messenger.send(Messages.LOGIN_FAILED_WRONG_CREDENTIALS);

        return passwordCorrect;
    }

    @Override
    public UserSession transform(LoginForm loginForm) {

        final Optional<User> optionalUser = userDao.findByUsername(loginForm.username());

        if (optionalUser.isEmpty()) {
            // TODO: display message that user could not be found

            throw new NoSuchElementException();
        }

        final User user = optionalUser.get();

        return new UserSession(user.getId(), user.getUsername());
    }

    @Override
    public void conclude(UserSession userSession) {

        try {
            onEnd.accept(userSession);
        } catch (Exception e) {
            messenger.send(Messages.TRANSITION_NOT_PERFORMED);
            throw e;
        }
    }

}
