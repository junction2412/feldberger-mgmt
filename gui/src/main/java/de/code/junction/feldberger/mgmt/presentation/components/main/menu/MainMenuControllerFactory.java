package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerListService;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewController;

public class MainMenuControllerFactory {

    private final PersistenceManager persistenceManager;

    public MainMenuControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }

    public FXController customers(Transition<Void, Customer> newCustomerTransition) {

        final var customerListService = new CustomerListService(persistenceManager.customerDao());

        return new CustomerOverviewController(customerListService, newCustomerTransition);
    }

    public FXController customerEditor(Customer customer,
                                       Transition<Integer, Integer> backTransition,
                                       Transition<Customer, Customer> saveTransition) {

        return new CustomerEditorController(new CustomerViewModel(customer), backTransition, saveTransition);
    }
}
