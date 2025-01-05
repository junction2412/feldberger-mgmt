package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import de.code.junction.feldberger.mgmt.presentation.components.common.TransitionFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.CustomerDashboard;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorPersistenceTransitionLifecycle;

import java.util.function.Consumer;

public class MainMenuTransitionFactory extends TransitionFactory {

    public MainMenuTransitionFactory(PersistenceManager persistenceManager, Messenger messenger) {

        super(persistenceManager, messenger);
    }

    public Transition<Customer, CustomerDashboard> customerEditorCustomerDashboard(Consumer<CustomerDashboard> onConclude) {

        final var customerService = new CustomerService(
                persistenceManager.customerDao(),
                persistenceManager.addressDao()
        );

        final var lifecycle = new CustomerEditorPersistenceTransitionLifecycle(
                customerService,
                messenger,
                onConclude
        );

        return new Transition<>(lifecycle);
    }
}
