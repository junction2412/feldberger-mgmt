package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuTransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
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


    private final PersistenceManager persistenceManager;

    public ApplicationControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }

    public FXController login(Transition<LoginForm, UserSession> loginTransition,
                              Transition<LoginForm, RegistrationForm> registrationTransition,
                              String username) {

        return new LoginController(loginTransition,
                registrationTransition,
                new LoginFormViewModel(username));
    }

    public FXController registration(Transition<RegistrationForm, UserSession> registrationTransition,
                                     Transition<RegistrationForm, LoginForm> loginTransition,
                                     String username) {

        return new RegistrationController(registrationTransition,
                loginTransition,
                new RegistrationFormViewModel(username));
    }

    public FXController mainMenu(MainMenuTransitionFactory transitionFactory,
                                 Transition<UserSession, LoginForm> logoutTransition,
                                 Transition<UserSession, UserSession> settingsTransition,
                                 int userID,
                                 String username) {

        return new MainMenuController(
                logoutTransition,
                settingsTransition,
                new UserSessionViewModel(userID, username),
                new MainMenuNavContext(transitionFactory, new MainMenuControllerFactory(persistenceManager), userID)
        );
    }
}
