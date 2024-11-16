package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginMainMenuTransitionManager;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationMainMenuTransitionManager;

import java.util.Objects;
import java.util.function.Consumer;

public class TransitionManagerFactory {

    private final PersistenceManager persistenceManager;
    private Messenger messenger;

    public TransitionManagerFactory(PersistenceManager persistenceManager) {

        this(persistenceManager, _ -> { /* default noop messenger */ });
    }

    public TransitionManagerFactory(PersistenceManager persistenceManager, Messenger messenger) {

        this.persistenceManager = persistenceManager;
        this.messenger = messenger;
    }

    public Messenger getMessenger() {

        return messenger;
    }

    public void setMessenger(Messenger messenger) {

        this.messenger = Objects.requireNonNull(messenger);
    }

    public TransitionManager<Credentials> loginToMainMenu(Consumer<Credentials> onTransition) {

        return new LoginMainMenuTransitionManager(messenger, onTransition, persistenceManager.userDao());
    }

    public TransitionManager<RegistrationForm> registrationToMainMenu(Consumer<RegistrationForm> onTransition) {

        return new RegistrationMainMenuTransitionManager(messenger, onTransition, persistenceManager.userDao());
    }
}
