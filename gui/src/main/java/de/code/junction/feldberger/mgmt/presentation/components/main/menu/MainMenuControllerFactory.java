package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;

public class MainMenuControllerFactory {

    private final PersistenceManager persistenceManager;

    public MainMenuControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
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
