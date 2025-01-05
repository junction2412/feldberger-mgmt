package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.MainMenu;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;

public class ApplicationLoginTransitionLifecycle implements TransitionLifecycle<LoginForm, MainMenu> {

    private final Messenger messenger;
    private final UserDataAccessObject userDao;
    private final Consumer<MainMenu> onEnd;

    public ApplicationLoginTransitionLifecycle(Messenger messenger,
                                               UserDataAccessObject userDao,
                                               Consumer<MainMenu> onEnd) {

        this.messenger = messenger;
        this.userDao = userDao;
        this.onEnd = onEnd;
    }

    @Override
    public boolean validate(LoginForm loginForm) {

        final var optionalUser = userDao.findByUsername(loginForm.username());

        if (optionalUser.isEmpty()) {
            messenger.send(Messages.LOGIN_FAILED_WRONG_CREDENTIALS);
            return false;
        }

        final var user = optionalUser.get();

        if (user.isInactive()) {

        }

        final var passwordSalt = user.getPasswordSalt();
        final var hashedPasswordInput = hashPassword(loginForm.password(), passwordSalt);
        final var passwordCorrect = hashedPasswordInput.equals(user.getPasswordHash());

        if (!passwordCorrect)
            messenger.send(Messages.LOGIN_FAILED_WRONG_CREDENTIALS);

        return passwordCorrect;
    }

    @Override
    public MainMenu transform(LoginForm loginForm) {

        final var optionalUser = userDao.findByUsername(loginForm.username());

        if (optionalUser.isEmpty()) {
            // TODO: display message that user could not be found
            throw new NoSuchElementException();
        }

        final var user = optionalUser.get();

        return new MainMenu(user);
    }

    @Override
    public void conclude(MainMenu route) {

        try {
            onEnd.accept(route);
        } catch (Exception e) {
            messenger.send(Messages.TRANSITION_NOT_PERFORMED);
            throw e;
        }
    }
}
