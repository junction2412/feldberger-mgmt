package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.login.ApplicationLoginTransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.view.registration.ApplicationRegistrationTransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationForm;

import java.util.function.Consumer;

/**
 * A factory class to be used to instantiate more complex {@link TransitionLifecycle} implementations.
 *
 * @author J. Murray
 */
public class ApplicationTransitionFactory extends TransitionFactory {

    public ApplicationTransitionFactory(PersistenceManager persistenceManager, Messenger messenger) {

        super(persistenceManager, messenger);
    }

    public Transition<RegistrationForm, Route<ApplicationRoute>> registration(Consumer<Route<ApplicationRoute>> onEnd) {

        return new Transition<>(new ApplicationRegistrationTransitionLifecycle(
                messenger,
                persistenceManager.userDao(),
                onEnd
        ));
    }

    public Transition<LoginForm, Route<ApplicationRoute>> login(Consumer<Route<ApplicationRoute>> onEnd) {

        return new Transition<>(new ApplicationLoginTransitionLifecycle(
                messenger,
                persistenceManager.userDao(),
                onEnd
        ));
    }
}
