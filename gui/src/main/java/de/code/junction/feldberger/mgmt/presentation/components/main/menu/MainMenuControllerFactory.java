package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerListService;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;

import java.util.function.Consumer;

public class MainMenuControllerFactory {

    private final PersistenceManager persistenceManager;

    public MainMenuControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }

    public FXController customerOverview(Runnable onNewCustomerClicked,
                                         Consumer<Integer> onEditCustomerClicked,
                                         Consumer<Integer> onViewCustomerClicked,
                                         String filter,
                                         int selectedCustomerId) {

        final var viewModel = new CustomerOverviewModel(selectedCustomerId, filter);
        final var customerListService = new CustomerListService(persistenceManager.customerDao());

        return new CustomerOverviewController(
                customerListService,
                viewModel,
                onNewCustomerClicked,
                onEditCustomerClicked,
                onViewCustomerClicked
        );
    }

    public FXController customerEditor(Runnable onBackClicked, Consumer<Customer> onSaveClicked, int customerId) {

        final var customer = persistenceManager.customerDao()
                .findById(customerId)
                .orElseGet(Customer::new);

        return new CustomerEditorController(new CustomerViewModel(customer), onBackClicked, onSaveClicked);
    }

    public FXController customerDashboard(Runnable onBackClicked,
                                          Runnable onEditCustomerClicked,
                                          Runnable onNewTransactionClicked,
                                          int customerId,
                                          int selectedTransactionId) {

        final var customer = persistenceManager.customerDao()
                .findById(customerId)
                .orElseThrow();

        return new CustomerDashboardController(
                new CustomerViewModel(customer),
                onBackClicked,
                onEditCustomerClicked,
                onNewTransactionClicked
        );
    }
}
