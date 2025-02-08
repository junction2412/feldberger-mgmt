package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavigator;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationViewModel;

public class ViewModelFactory {

    private final Messenger messenger;
    private final ServiceFactory serviceFactory;

    public ViewModelFactory(Messenger messenger, ServiceFactory serviceFactory) {

        this.messenger = messenger;
        this.serviceFactory = serviceFactory;
    }

    public LoginViewModel login(Navigator<ApplicationRoute> appNavigator, String username) {
        return new LoginViewModel(messenger, appNavigator, serviceFactory.loginService(), username);
    }

    public RegistrationViewModel registration(Navigator<ApplicationRoute> appNavigator, String username) {

        return new RegistrationViewModel(
                messenger,
                appNavigator,
                serviceFactory.registrationService(),
                username
        );
    }

    public MainMenuViewModel mainMenu(Navigator<ApplicationRoute> appNavigator,
                                      MainMenuNavigator subNavigator,
                                      int userId,
                                      String username) {

        return new MainMenuViewModel(messenger, appNavigator, subNavigator, userId, username);
    }

    public CustomerOverviewModel customerOverview(Navigator<MainMenuRoute> navigator) {
        return new CustomerOverviewModel(messenger, navigator, serviceFactory.customerListService());
    }

    public CustomerEditorViewModel customerEditor(Navigator<MainMenuRoute> navigator, MainMenuRoute backRoute, Customer customer) {
        return new CustomerEditorViewModel(messenger, navigator, serviceFactory.customerEditor(), backRoute, customer);
    }

    public CustomerDashboardViewModel customerDashboard(Navigator<MainMenuRoute> navigator, Customer customer) {
        return new CustomerDashboardViewModel(messenger, navigator, customer);
    }
}
