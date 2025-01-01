package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceRegistry;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginController;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.*;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationController;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationFormViewModel;

import java.util.function.Consumer;

/**
 * A factory class to construct {@link FXController} instances.
 */
public class ApplicationControllerFactory {

    public ApplicationControllerFactory() {
    }

    public FXController registration(Consumer<String> onBackClicked,
                                     Consumer<RegistrationForm> onSubmitClicked,
                                     String username) {

        return new RegistrationController(new RegistrationFormViewModel(username), onBackClicked, onSubmitClicked);
    }

    public FXController login(Consumer<LoginForm> onSubmitClicked,
                              Consumer<String> onRegisterClicked,
                              String username) {

        return new LoginController(new LoginViewModel(username), onSubmitClicked, onRegisterClicked);
    }

    public FXController mainMenu(Runnable onLogoutClicked,
                                 Consumer<UserSession> onSettingsClicked,
                                 User user, PreferenceRegistry preferenceRegistry) {

        final var selectedSubviewPreference = preferenceRegistry.<String>getValue(MainMenuPreferences.SELECTED_SUBVIEW);
        final var selectedSubview = selectedSubviewPreference != null
                ? Subview.valueOf(selectedSubviewPreference)
                : null;

        final var viewModel = new MainMenuViewModel(user.getId(), user.getUsername(), selectedSubview);

        viewModel.selectedSubviewProperty().addListener((_, _, value) ->
                preferenceRegistry.setValue(MainMenuPreferences.SELECTED_SUBVIEW, String.valueOf(value))
        );

        return new MainMenuController(viewModel, onLogoutClicked, onSettingsClicked);
    }
}
