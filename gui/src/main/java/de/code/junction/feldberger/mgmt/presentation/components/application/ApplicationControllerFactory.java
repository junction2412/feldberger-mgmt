package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginFormViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.Subview;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationController;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationFormViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A factory class to construct {@link FXController} instances.
 */
public class ApplicationControllerFactory {

    private final PersistenceManager persistenceManager;

    public ApplicationControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }

    public FXController registration(Transition<RegistrationForm, ?> registrationTransition,
                                     Transition<Void, ?> loginTransition,
                                     Map<String, Object> cache) {

        final var username = (String) cache.getOrDefault("username", "");
        final var viewModel = new RegistrationFormViewModel(username);

        viewModel.usernameProperty().addListener((_, _, value) ->
                cache.put("username", value)
        );

        return new RegistrationController(registrationTransition, loginTransition, viewModel);
    }

    public FXController login(Map<String, Object> cache,
                              Consumer<LoginForm> onSubmitClicked,
                              Consumer<String> onRegisterClicked) {

        final var username = (String) cache.getOrDefault("username", "");
        final var viewModel = new LoginFormViewModel(username);

        viewModel.usernameProperty().addListener((_, _, value) ->
                cache.put("username", value)
        );

        return new LoginController(viewModel, onSubmitClicked, onRegisterClicked);
    }

    public FXController mainMenu(Transition<Void, ?> logoutTransition,
                                 HashMap<String, Object> cache) {

        final var userId = (int) cache.get("userId");
        final var username = persistenceManager.userDao()
                .findById(userId)
                .map(User::getUsername)
                .orElseThrow();

        final var selectedSubview = cache.containsKey("selectedSubviewEnumValue")
                ? Subview.valueOf((String) cache.get("selectedSubviewEnumValue"))
                : null;

        final var viewModel = new MainMenuViewModel(userId, username, selectedSubview);

        viewModel.selectedSubviewProperty().addListener((_, _, value) ->
                cache.put("selectedSubviewEnumValue", value)
        );

        return new MainMenuController(
                logoutTransition,
                Transition.immediate(_ -> System.out.println("Settings")),
                viewModel
        );
    }
}
