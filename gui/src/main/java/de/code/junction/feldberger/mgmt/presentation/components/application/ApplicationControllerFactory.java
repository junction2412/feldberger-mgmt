package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuController;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.UserSession;

import java.util.function.Consumer;

/**
 * A factory class to construct {@link FXController} instances.
 */
public class ApplicationControllerFactory {

    public FXController mainMenu(Runnable onLogoutClicked,
                                 Consumer<UserSession> onSettingsClicked,
                                 User user) {

        final var viewModel = new MainMenuViewModel(user.getId(), user.getUsername(), null);
        return new MainMenuController(viewModel, onLogoutClicked, onSettingsClicked);
    }
}
