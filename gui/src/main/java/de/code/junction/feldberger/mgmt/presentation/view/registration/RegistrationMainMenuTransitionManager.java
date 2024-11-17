package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.MessageType;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.AbstractTransitionManager;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import static de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil.getMessageStringResources;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class RegistrationMainMenuTransitionManager extends AbstractTransitionManager<RegistrationForm> {

    private final UserDataAccessObject userDao;

    public RegistrationMainMenuTransitionManager(Messenger messenger,
                                                 Consumer<RegistrationForm> onTransition,
                                                 UserDataAccessObject userDao) {

        super(messenger, onTransition);

        this.userDao = userDao;
    }

    @Override
    public void transition(RegistrationForm registrationForm) {

        if (!isValid(registrationForm)) return;

        onTransition.accept(registrationForm);
    }

    private boolean isValid(RegistrationForm registrationForm) {

        final String username = registrationForm.username();
        final String password = registrationForm.password();

        final boolean arePasswordInputsEqual = password.equals(registrationForm.repeatPassword());
        final boolean isPasswordMinimumLength = 7 < password.length();
        final boolean isPasswordNotMaxLength = password.length() < 65;
        // TODO: implement additional checks and fix alerts
        final boolean isUsernameNotEmpty =  !username.isEmpty();
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

        if (!isUsernameNotTaken)
            reasons.append(bundle.getString("registration.failed.content.reason.username.taken")).append("\n");

        if (!(arePasswordInputsEqual && isPasswordMinimumLength && isPasswordNotMaxLength && isUsernameNotTaken)) {

            final String title = bundle.getString("registration.failed.title");
            final String header = bundle.getString("registration.failed.header");
            final String content = bundle.getString("registration.failed.content.format").formatted(reasons);

            messenger.send(new Message(MessageType.WARNING, title, header, content));

            return false;
        }

        return true;
    }
}
