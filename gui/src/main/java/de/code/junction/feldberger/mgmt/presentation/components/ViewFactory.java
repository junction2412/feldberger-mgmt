package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavigator;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorView;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverview;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginView;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuView;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationView;

public class ViewFactory {

    private final ViewModelFactory viewModelFactory;

    public ViewFactory(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }

    public FXLoadable login(String username) {
        return new LoginView(viewModelFactory.login(username));
    }

    public FXLoadable registration(String username) {
        return new RegistrationView(viewModelFactory.registration(username));
    }

    public FXLoadable mainMenu(int userId, String username) {
        final var navigator = new MainMenuNavigator(this, null);
        return new MainMenuView(viewModelFactory.mainMenu(navigator, userId, username));
    }

    public FXLoadable customerOverview(Navigator<MainMenuRoute> navigator) {
        return new CustomerOverview(viewModelFactory.customerOverview(navigator));
    }

    public FXLoadable customerEditor(Navigator<MainMenuRoute> navigator, MainMenuRoute backRoute, Customer customer) {
        return new CustomerEditorView(viewModelFactory.customerEditor(navigator, backRoute, customer));
    }
}
