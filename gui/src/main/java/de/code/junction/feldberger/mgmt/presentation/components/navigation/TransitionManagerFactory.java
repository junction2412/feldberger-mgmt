package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginMainMenuTransitionManager;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationMainMenuTransitionManager;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A factory class to be used to instantiate more complex {@link TransitionManager} implementations.
 *
 * @author J. Murray
 */
public class TransitionManagerFactory {

    /**
     * A {@link PersistenceManager} that is used to inject necessary {@link DataAccessObject}s.
     */
    private final PersistenceManager persistenceManager;

    /**
     * The {@link Messenger} that is injected.
     */
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

    /**
     * Provides the login->mainMenu transition manager.
     *
     * @param onTransition transition ui logic to be performed
     * @return login->mainMenu transition manager
     */
    public TransitionManager<Credentials> loginToMainMenu(Consumer<Credentials> onTransition) {

        return new LoginMainMenuTransitionManager(messenger, onTransition, persistenceManager.userDao());
    }

    /**
     * Provides the registration->mainMenu transition manager
     *
     * @param onTransition transition ui logic to be performed
     * @return registration->mainMenu transition manager
     */
    public TransitionManager<RegistrationForm> registrationToMainMenu(Consumer<RegistrationForm> onTransition) {

        return new RegistrationMainMenuTransitionManager(messenger, onTransition, persistenceManager.userDao());
    }
}
