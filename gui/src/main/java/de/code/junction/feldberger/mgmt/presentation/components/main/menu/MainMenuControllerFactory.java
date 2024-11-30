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

    public FXController customers(Transition<Customer, ?> viewCustomerTransition,
                                  Transition<Customer, ?> editCustomerTransition,
                                  Transition<Void, ?> newCustomerTransition) {

        final var customerListService = new CustomerListService(persistenceManager.customerDao());

        return new CustomerOverviewController(customerListService, viewCustomerTransition, editCustomerTransition, newCustomerTransition);
    }

    public FXController customerEditor(Customer customer,
                                       Transition<Customer, ?> backTransition,
                                       Transition<Customer, ?> saveTransition) {

        return new CustomerEditorController(new CustomerViewModel(customer), backTransition, saveTransition);
    }
}
