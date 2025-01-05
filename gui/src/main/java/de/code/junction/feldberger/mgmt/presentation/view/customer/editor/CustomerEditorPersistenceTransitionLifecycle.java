package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.CustomerDashboard;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;

import java.util.function.Consumer;

public class CustomerEditorPersistenceTransitionLifecycle implements TransitionLifecycle<Customer, CustomerDashboard> {

    private final CustomerService customerService;
    private final Messenger messenger;
    private final Consumer<CustomerDashboard> onConclude;

    public CustomerEditorPersistenceTransitionLifecycle(CustomerService customerService,
                                                        Messenger messenger,
                                                        Consumer<CustomerDashboard> onConclude) {

        this.customerService = customerService;
        this.messenger = messenger;
        this.onConclude = onConclude;
    }

    @Override
    public boolean validate(Customer customer) {

        final var optionalCustomer = customerService.findCustomerByIdNo(customer.getIdNo());

        if (optionalCustomer.isPresent()) {
            final var customerFromDb = optionalCustomer.get();
            final var isSameRecord = customer.getId().equals(customerFromDb.getId());

            if (!isSameRecord)
                messenger.send(Messages.CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION);

            return isSameRecord;
        }

        return true;
    }

    @Override
    public CustomerDashboard transform(Customer customer) {

        customerService.persistCustomer(customer);
        return new CustomerDashboard(customer.getId());
    }

    @Override
    public void conclude(CustomerDashboard route) {

        onConclude.accept(route);
    }
}
