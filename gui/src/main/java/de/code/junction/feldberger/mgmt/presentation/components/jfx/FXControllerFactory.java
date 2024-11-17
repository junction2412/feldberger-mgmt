package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.components.service.ServiceFactory;
import de.code.junction.feldberger.mgmt.presentation.model.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginFormViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationController;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationFormViewModel;

/**
 * A factory class to construct {@link FXController} instances.
 */
public class FXControllerFactory {

    /**
     * A factory to eventually inject services into the controllers.
     */
    private final ServiceFactory serviceFactory;

    public FXControllerFactory(ServiceFactory serviceFactory) {

        this.serviceFactory = serviceFactory;
    }

    public FXController login(TransitionOrchestrator<LoginForm, User> loginTransition,
                              TransitionOrchestrator<String, String> registrationTransition,
                              String username) {

        return new LoginController(loginTransition,
                registrationTransition,
                new LoginFormViewModel(username));
    }

    public FXController registration(TransitionOrchestrator<RegistrationForm, User> registrationTransition,
                                     TransitionOrchestrator<String, String> loginTransition,
                                     String username) {

        return new RegistrationController(registrationTransition,
                loginTransition,
                new RegistrationFormViewModel(username));
    }
}
