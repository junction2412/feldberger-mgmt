package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginMainMenuTransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationMainMenuTransitionLifecycle;

import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.*;

/**
 * A factory class to be used to instantiate more complex {@link TransitionLifecycle} implementations.
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
    public Transition<LoginForm, UserSession> loginSession(Consumer<UserSession> onTransition) {

        return new Transition<>(new LoginMainMenuTransitionLifecycle(messenger, persistenceManager.userDao(),
                onTransition));
    }

    /**
     * Provides the login->registration transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return login->registration transition
     */
    public Transition<LoginForm, RegistrationForm> loginRegistration(Consumer<RegistrationForm> onTransition) {

        return new Transition<>(TransitionLifecycle.bypass(form -> new RegistrationForm(form.username()),
                onTransition));
    }

    /**
     * Provides the registration->login transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return registration->login transition
     */
    public Transition<RegistrationForm, LoginForm> registrationLogin(Consumer<LoginForm> onTransition) {

        return new Transition<>(TransitionLifecycle.bypass(form -> new LoginForm(form.username()), onTransition));
    }

    /**
     * Provides the registration->mainMenu transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return registration->mainMenu transition
     */
    public Transition<RegistrationForm, UserSession> registrationSession(Consumer<UserSession> onTransition) {

        return new Transition<>(new RegistrationMainMenuTransitionLifecycle(messenger, persistenceManager.userDao(),
                onTransition));
    }

    /**
     * Provides the mainMenu->login transition.
     *
     * @param onTransition transition ui logic to be performed
     * @return mainMenu->login transition
     */
    public Transition<UserSession, LoginForm> sessionLogin(Consumer<LoginForm> onTransition) {

        return Transition.bypass(session -> new LoginForm(session.username()), onTransition);
    }
}
