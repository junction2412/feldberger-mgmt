package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.salt;
import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getMessageStringResources;

public class ApplicationRegistrationTransitionLifecycle implements TransitionLifecycle<RegistrationForm, Route<ApplicationRoute>> {

    private final Messenger messenger;
    private final UserDataAccessObject userDao;
    private final Consumer<Route<ApplicationRoute>> onEnd;

    public ApplicationRegistrationTransitionLifecycle(Messenger messenger,
                                                      UserDataAccessObject userDao,
                                                      Consumer<Route<ApplicationRoute>> onEnd) {

        this.messenger = messenger;
        this.userDao = userDao;
        this.onEnd = onEnd;
    }

    @Override
    public boolean validate(RegistrationForm registrationForm) {

        final String username = registrationForm.username();
        final String password = registrationForm.password();

        final boolean arePasswordInputsEqual = password.equals(registrationForm.repeatPassword());
        final boolean isPasswordMinimumLength = 7 < password.length();
        final boolean isPasswordNotMaxLength = password.length() < 65;
        final boolean isUsernameNotEmpty = !username.isEmpty();
        final boolean isUsernameNotTaken = userDao.findByUsername(username).isEmpty();

        final StringBuilder reasons = new StringBuilder();
        final ResourceBundle bundle = getMessageStringResources();

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

        final boolean isPasswordValid = arePasswordInputsEqual && isPasswordMinimumLength && isPasswordNotMaxLength;
        final boolean isUsernameValid = isUsernameNotEmpty && isUsernameNotTaken;

        if (!(isPasswordValid && isUsernameValid)) {

            final String title = bundle.getString("registration.failed.title");
            final String header = bundle.getString("registration.failed.header");
            final String content = bundle.getString("registration.failed.content.format").formatted(reasons);

            messenger.send(new Message(MessageType.WARNING, title, header, content));

            return false;
        }

        return true;
    }

    @Override
    public Route<ApplicationRoute> transform(RegistrationForm registrationForm) {

        final var passwordSalt = salt();
        final var passwordHash = hashPassword(
                registrationForm.password(),
                passwordSalt
        );

        final var user = new User(
                registrationForm.username(),
                passwordHash,
                passwordSalt
        );

        userDao.persistUser(user);

        final var map = new HashMap<String, Object>();
        map.put("userId", user.getId());

        return new Route<>(
                ApplicationRoute.MAIN_MENU,
                map
        );
    }

    @Override
    public void conclude(Route<ApplicationRoute> route) {

        try {
            onEnd.accept(route);
        } catch (Exception e) {
            messenger.send(Messages.TRANSITION_NOT_PERFORMED);
            throw e;
        }
    }
}
