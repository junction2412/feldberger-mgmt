package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;

import java.util.ResourceBundle;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.hashPassword;
import static de.code.junction.feldberger.mgmt.presentation.util.HashUtil.salt;
import static de.code.junction.feldberger.mgmt.presentation.util.MessageUtil.transitionNotPerformed;
import static de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil.getMessageStringResources;

public class RegistrationTransition implements Transition<RegistrationForm, User> {

    private final Messenger messenger;
    private final UserDataAccessObject userDao;
    private final Consumer<User> onEnd;

    public RegistrationTransition(Messenger messenger,
                                  UserDataAccessObject userDao,
                                  Consumer<User> onEnd) {

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
            reasons.append(bundle.getString("registration.failed.content.reason.password.inputs.not.equal"))
                    .append("\n");

        if (!isPasswordMinimumLength)
            reasons.append(bundle.getString("registration.failed.content.reason.password.too.short")).append("\n");

        if (!isPasswordNotMaxLength)
            reasons.append(bundle.getString("registration.failed.content.reason.password.too.long")).append("\n");

        if (!isUsernameNotEmpty)
            reasons.append(bundle.getString("registration.failed.content.reason.username.empty")).append("\n");

        if (!isUsernameNotTaken)
            reasons.append(bundle.getString("registration.failed.content.reason.username.taken")).append("\n");

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
    public User convert(RegistrationForm registrationForm) {

        final String passwordSalt = salt();
        final String passwordHash = hashPassword(registrationForm.password(), salt());

        final User user = new User(registrationForm.username(), passwordHash, passwordSalt);

        userDao.persistUser(user);

        return user;
    }

    @Override
    public void end(User user) {

        try {
            onEnd.accept(user);
        } catch (Exception e) {
            messenger.send(transitionNotPerformed());
            throw e;
        }
    }
}
