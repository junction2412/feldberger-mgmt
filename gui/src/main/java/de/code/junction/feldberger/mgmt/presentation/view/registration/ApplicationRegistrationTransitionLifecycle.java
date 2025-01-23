package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.Login;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;

import java.text.MessageFormat;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute.MainMenu;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.salt;
import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getMessageStringResources;

public class ApplicationRegistrationTransitionLifecycle implements TransitionLifecycle<RegistrationForm, ApplicationRoute> {

    private final Messenger messenger;
    private final UserDataAccessObject userDao;
    private final Consumer<ApplicationRoute> onEnd;

    public ApplicationRegistrationTransitionLifecycle(Messenger messenger,
                                                      UserDataAccessObject userDao,
                                                      Consumer<ApplicationRoute> onEnd) {

        this.messenger = messenger;
        this.userDao = userDao;
        this.onEnd = onEnd;
    }

    @Override
    public boolean validate(RegistrationForm registrationForm) {

        final var username = registrationForm.username();
        final var password = registrationForm.password();

        final var arePasswordInputsEqual = password.equals(registrationForm.repeatPassword());
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

            messenger.send(new Message(MessageType.WARNING, title, header, content));

            return false;
        }

        return true;
    }

    @Override
    public ApplicationRoute transform(RegistrationForm registrationForm) {

        final var passwordSalt = salt();
        final var passwordHash = hashPassword(registrationForm.password(), passwordSalt);
        final var user = new User(registrationForm.username(), passwordHash, passwordSalt);

        final var AT_LEAST_ONE_USER_EXISTS = 0 < userDao.countAll();
        final ApplicationRoute route;

        if (AT_LEAST_ONE_USER_EXISTS) {
            user.setInactive();
            route = new Login(user.getUsername());

            messenger.send(Messages.REGISTRATION_SUCCEEDED_USER_INACTIVE);
        } else {
            route = new MainMenu(user);
        }

        userDao.persistUser(user);

        return route;
    }

    @Override
    public void conclude(ApplicationRoute route) {

        try {
            onEnd.accept(route);
        } catch (Exception e) {
            messenger.send(Messages.TRANSITION_NOT_PERFORMED);
            throw e;
        }
    }
}
