package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorCustomerDashboardTransitionLifecycle;

import java.util.function.Consumer;

public class MainMenuTransitionFactory extends TransitionFactory {


    public MainMenuTransitionFactory(PersistenceManager persistenceManager, Messenger messenger) {

        super(persistenceManager, messenger);
    }

    public Transition<Customer, Customer> customerEditorCustomerDashboard(Consumer<Customer> onConclude) {

        final var customerService = new CustomerService(
                persistenceManager.customerDao(),
                persistenceManager.addressDao()
        );

        return new Transition<>(new CustomerEditorCustomerDashboardTransitionLifecycle(customerService, messenger, onConclude));
    }
}
