package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavigator;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardView;
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

    public FXLoadable login(Navigator<ApplicationRoute> navigator, String username) {
        return new LoginView(viewModelFactory.login(navigator, username));
    }

    public FXLoadable registration(Navigator<ApplicationRoute> navigator, String username) {
        return new RegistrationView(viewModelFactory.registration(navigator, username));
    }

    public FXLoadable mainMenu(Navigator<ApplicationRoute> navigator, int userId, String username) {

        final var subNavigator = new MainMenuNavigator(this);
        return new MainMenuView(viewModelFactory.mainMenu(navigator, subNavigator, userId, username));
    }

    public FXLoadable customerOverview(Navigator<MainMenuRoute> navigator) {
        return new CustomerOverview(viewModelFactory.customerOverview(navigator));
    }

    public FXLoadable customerEditor(Navigator<MainMenuRoute> navigator, MainMenuRoute backRoute, Customer customer) {
        return new CustomerEditorView(viewModelFactory.customerEditor(navigator, backRoute, customer));
    }

    public FXLoadable customerDashboard(Navigator<MainMenuRoute> navigator, Customer customer) {
        return new CustomerDashboardView(viewModelFactory.customerDashboard(navigator, customer));
    }
}
