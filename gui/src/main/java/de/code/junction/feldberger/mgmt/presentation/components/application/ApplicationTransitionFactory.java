package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginMainMenuTransition;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationMainMenuTransition;

import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.*;

/**
 * A factory class to be used to instantiate more complex {@link Transition} implementations.
 *
 * @author J. Murray
 */
public class ApplicationTransitionFactory extends TransitionFactory {

    public ApplicationTransitionFactory(PersistenceManager persistenceManager, Messenger messenger) {

        super(persistenceManager, messenger);
    }

    /**
     * Provides the login->mainMenu transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return login->mainMenu transition
     */
    public TransitionOrchestrator<LoginForm, UserSession> loginSession(Consumer<UserSession> onTransition) {

        return new TransitionOrchestrator<>(new LoginMainMenuTransition(messenger, persistenceManager.userDao(),
                onTransition));
    }

    /**
     * Provides the login->registration transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return login->registration transition
     */
    public TransitionOrchestrator<LoginForm, RegistrationForm> loginRegistration(Consumer<RegistrationForm> onTransition) {

        return new TransitionOrchestrator<>(Transition.bypass(form -> new RegistrationForm(form.username()),
                onTransition));
    }

    /**
     * Provides the registration->login transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return registration->login transition
     */
    public TransitionOrchestrator<RegistrationForm, LoginForm> registrationLogin(Consumer<LoginForm> onTransition) {

        return new TransitionOrchestrator<>(Transition.bypass(form -> new LoginForm(form.username()), onTransition));
    }

    /**
     * Provides the registration->mainMenu transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return registration->mainMenu transition
     */
    public TransitionOrchestrator<RegistrationForm, UserSession> registrationSession(Consumer<UserSession> onTransition) {

        return new TransitionOrchestrator<>(new RegistrationMainMenuTransition(messenger, persistenceManager.userDao(),
                onTransition));
    }

    /**
     * Provides the mainMenu->login transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return mainMenu->login transition
     */
    public TransitionOrchestrator<UserSession, LoginForm> sessionLogin(Consumer<LoginForm> onTransition) {

        return new TransitionOrchestrator<>(Transition.bypass(session -> new LoginForm(session.username()),
                onTransition));
    }
}
