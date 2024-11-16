package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManager;
import de.code.junction.feldberger.mgmt.presentation.components.service.ServiceFactory;
import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationController;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationViewModel;

public class FXControllerFactory {

    private final ServiceFactory serviceFactory;

    public FXControllerFactory(ServiceFactory serviceFactory) {

        this.serviceFactory = serviceFactory;
    }

    public LoginController login(TransitionManager<Credentials> mainMenuTransitionManager,
                                 TransitionManager<String> registrationTransitionManager,
                                 String username) {

        return new LoginController(mainMenuTransitionManager,
                registrationTransitionManager,
                new LoginViewModel(username));
    }

    public RegistrationController registration(TransitionManager<RegistrationForm> mainMenuTransitionManager,
                                               TransitionManager<String> registrationTransitionManager,
                                               String username) {

        return new RegistrationController(mainMenuTransitionManager,
                registrationTransitionManager,
                new RegistrationViewModel(username));
    }
}
