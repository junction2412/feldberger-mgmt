package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.Transition;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.LoginForm;
import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.UserSession;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;
import static de.code.junction.feldberger.mgmt.presentation.util.MessageUtil.transitionNotPerformed;
import static de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil.getMessageStringResources;

public class LoginMainMenuTransition implements Transition<LoginForm, UserSession> {

    private final Messenger messenger;
    private final UserDataAccessObject userDao;
    private final Consumer<UserSession> onEnd;

    public LoginMainMenuTransition(Messenger messenger,
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
            messenger.send(wrongCredentials());
            return false;
        }

        final User user = optionalUser.get();

        final String passwordSalt = user.getPasswordSalt();
        final String hashedPasswordInput = hashPassword(loginForm.password(), passwordSalt);

        final boolean passwordCorrect = hashedPasswordInput.equals(user.getPasswordHash());

        if (!passwordCorrect)
            messenger.send(wrongCredentials());

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

        return new UserSession(user.getID(), user.getUsername());
    }

    @Override
    public void conclude(UserSession userSession) {

        try {
            onEnd.accept(userSession);
        } catch (Exception e) {
            messenger.send(transitionNotPerformed());
            throw e;
        }
    }

    private static Message wrongCredentials() {

        final ResourceBundle bundle = getMessageStringResources();

        return new Message(
                MessageType.WARNING,
                bundle.getString("login.failed.wrong.credentials.title"),
                bundle.getString("login.failed.wrong.credentials.header"),
                bundle.getString("login.failed.wrong.credentials.content")
        );
    }
}
