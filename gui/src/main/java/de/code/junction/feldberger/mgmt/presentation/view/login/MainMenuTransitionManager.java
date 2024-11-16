package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.UserDataAccessObject;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.AbstractTransitionManager;

import java.util.function.Consumer;

public class MainMenuTransitionManager extends AbstractTransitionManager<Credentials> {

    public MainMenuTransitionManager(Messenger messenger, Consumer<Credentials> onTransition,
                                     UserDataAccessObject userDao) {

        super(messenger, onTransition);
    }

    @Override
    public void transition(Credentials credentials) {

    }
}
