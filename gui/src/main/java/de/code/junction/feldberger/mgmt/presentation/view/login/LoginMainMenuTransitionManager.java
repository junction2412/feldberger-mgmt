package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.AbstractTransitionManager;
import de.code.junction.feldberger.mgmt.presentation.model.LoginForm;

import java.util.function.Consumer;

public class LoginMainMenuTransitionManager extends AbstractTransitionManager<LoginForm> {

    public LoginMainMenuTransitionManager(Messenger messenger, Consumer<LoginForm> onTransition,
                                          UserDataAccessObject userDao) {

        super(messenger, onTransition);
    }

    @Override
    public void transition(LoginForm loginForm) {

    }
}
