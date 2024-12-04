package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerListService;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;
import javafx.application.Platform;

public class MainMenuControllerFactory {

    private final PersistenceManager persistenceManager;
    private final CustomerOverviewModel customerOverviewModel;

    public MainMenuControllerFactory(PersistenceManager persistenceManager,
                                     CustomerOverviewModel customerOverviewModel) {

        this.persistenceManager = persistenceManager;
        this.customerOverviewModel = customerOverviewModel;
    }

    public FXController customerOverview(Transition<Customer, ?> viewCustomerTransition,
                                         Transition<Customer, ?> editCustomerTransition,
                                         Transition<Void, ?> newCustomerTransition, int customerId) {

        if (customerId != 0)
            Platform.runLater(() -> customerOverviewModel.setSelectedCustomerId(customerId));

        final var customerListService = new CustomerListService(persistenceManager.customerDao());

        return new CustomerOverviewController(
                customerListService,
                viewCustomerTransition,
                editCustomerTransition,
                newCustomerTransition,
                customerOverviewModel
        );
    }

    public FXController customerEditor(Customer customer,
                                       Transition<Customer, ?> backTransition,
                                       Transition<Customer, ?> saveTransition) {

        return new CustomerEditorController(
                new CustomerViewModel(customer),
                backTransition,
                saveTransition
        );
    }

    public FXController customerDashboard(Customer customer,
                                          Transition<Customer, ?> backTransition,
                                          Transition<Customer, ?> editCustomerTransition,
                                          Transition<Customer, ?> newTransactionTransition) {

        return new CustomerDashboardController(
                new CustomerViewModel(customer),
                backTransition,
                editCustomerTransition,
                newTransactionTransition
        );
    }
}
