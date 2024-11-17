package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.model.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginTransition;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationTransition;

import java.util.function.Consumer;

/**
 * A factory class to be used to instantiate more complex {@link Transition} implementations.
 *
 * @author J. Murray
 */
public class TransitionFactory {

    /**
     * A {@link PersistenceManager} that is used to inject necessary {@link DataAccessObject}s.
     */
    private final PersistenceManager persistenceManager;

    /**
     * The {@link Messenger} that is injected.
     */
    private final Messenger messenger;

    public TransitionFactory(PersistenceManager persistenceManager, Messenger messenger) {

        this.persistenceManager = persistenceManager;
        this.messenger = messenger;
    }

    /**
     * Provides the login->mainMenu transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return login->mainMenu transition
     */
    public TransitionOrchestrator<LoginForm, User> loginTransition(Consumer<User> onTransition) {

        return new TransitionOrchestrator<>(new LoginTransition(
                messenger,
                persistenceManager.userDao(),
                onTransition
        ));
    }

    /**
     * Provides the registration->mainMenu transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return registration->mainMenu transition
     */
    public TransitionOrchestrator<RegistrationForm, User> registrationTransition(Consumer<User> onTransition) {

        return new TransitionOrchestrator<>(new RegistrationTransition(messenger, persistenceManager.userDao(),
                onTransition));
    }
}
