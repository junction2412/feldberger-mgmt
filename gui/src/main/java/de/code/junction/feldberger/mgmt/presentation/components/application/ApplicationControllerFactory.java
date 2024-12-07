package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.cache.RouteRecreationQueue;
import de.code.junction.feldberger.mgmt.presentation.components.common.SessionManager;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuControllerFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuTransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginFormViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.Subview;
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

    public FXController login(Transition<LoginForm, ?> loginTransition,
                              Transition<LoginForm, ?> registrationTransition,
                              String username) {

        return new LoginController(
                loginTransition,
                registrationTransition,
                new LoginFormViewModel(username)
        );
    }

    public FXController registration(Transition<RegistrationForm, ?> registrationTransition,
                                     Transition<RegistrationForm, ?> loginTransition,
                                     String username) {

        return new RegistrationController(
                registrationTransition,
                loginTransition,
                new RegistrationFormViewModel(username)
        );
    }

    public FXController mainMenu(MainMenuTransitionFactory transitionFactory,
                                 Transition<UserSession, ?> logoutTransition,
                                 Transition<UserSession, ?> settingsTransition,
                                 int userId,
                                 String username) {

        final var cache = RouteRecreationQueue.getInstance().current().getCache();
        final var selectedSubview = cache.containsKey("selectedSubviewEnumValue")
                ? Subview.valueOf((String) cache.get("selectedSubviewEnumValue"))
                : null;

        final var session = SessionManager.getInstance().retrieveSession(userId);
        final var customerOverviewModel = session.getCustomerOverviewModel();
        final var mainMenuViewModel = new MainMenuViewModel(
                userId,
                username,
                selectedSubview
        );

        final var mainControllerFactory = new MainMenuControllerFactory(
                persistenceManager
        );

        return new MainMenuController(
                logoutTransition,
                settingsTransition,
                mainMenuViewModel,
                new MainMenuNavContext(
                        transitionFactory,
                        mainControllerFactory,
                        userId
                )
        );
    }
}
