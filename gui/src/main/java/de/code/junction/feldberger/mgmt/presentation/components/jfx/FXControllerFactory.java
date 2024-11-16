package de.code.junction.feldberger.mgmt.presentation.components.jfx;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManager;
import de.code.junction.feldberger.mgmt.presentation.components.service.ServiceFactory;
import de.code.junction.feldberger.mgmt.presentation.view.login.Credentials;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginViewModel;

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
}
