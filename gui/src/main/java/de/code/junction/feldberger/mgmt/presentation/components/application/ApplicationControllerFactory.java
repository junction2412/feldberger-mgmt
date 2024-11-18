package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuTransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginFormViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.UserSessionViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationController;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationFormViewModel;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.*;

/**
 * A factory class to construct {@link FXController} instances.
 */
public class ApplicationControllerFactory {


    public ApplicationControllerFactory() {
    }

    public FXController login(TransitionOrchestrator<LoginForm, UserSession> loginTransition,
                              TransitionOrchestrator<LoginForm, RegistrationForm> registrationTransition,
                              String username) {

        return new LoginController(loginTransition,
                registrationTransition,
                new LoginFormViewModel(username));
    }

    public FXController registration(TransitionOrchestrator<RegistrationForm, UserSession> registrationTransition,
                                     TransitionOrchestrator<RegistrationForm, LoginForm> loginTransition,
                                     String username) {

        return new RegistrationController(registrationTransition,
                loginTransition,
                new RegistrationFormViewModel(username));
    }

    public FXController mainMenu(MainMenuTransitionFactory transitionFactory,
                                 TransitionOrchestrator<UserSession, LoginForm> logoutTransition,
                                 TransitionOrchestrator<UserSession, UserSession> settingsTransition,
                                 int userID,
                                 String username) {

        return new MainMenuController(
                logoutTransition,
                settingsTransition,
                new UserSessionViewModel(userID, username),
                new MainMenuNavContext(transitionFactory, new MainMenuControllerFactory())
        );
    }
}
