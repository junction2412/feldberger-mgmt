package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;

import java.util.Optional;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavRoute.CustomerDashboard;

public class CustomerEditorCustomerDashboardTransitionLifecycle implements TransitionLifecycle<Customer, CustomerDashboard> {

    private final CustomerService customerService;
    private final Messenger messenger;
    private final Consumer<CustomerDashboard> onConclude;

    public CustomerEditorCustomerDashboardTransitionLifecycle(CustomerService customerService,
                                                              Messenger messenger,
                                                              Consumer<CustomerDashboard> onConclude) {

        this.customerService = customerService;
        this.messenger = messenger;
        this.onConclude = onConclude;
    }

    @Override
    public boolean validate(Customer customer) {

        final Optional<Customer> optionalCustomer = customerService.findCustomerByIdNo(customer.getIdNo());

        if (optionalCustomer.isPresent()) {
            final var customerFromDb = optionalCustomer.get();

            final boolean isSameRecord = customer.getId().equals(customerFromDb.getId());

            if (!isSameRecord)
                messenger.send(Messages.CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION);

            return isSameRecord;
        }

        return true;
    }

    @Override
    public CustomerDashboard transform(Customer customer) {

        return new CustomerDashboard(customer);
    }

    @Override
    public void conclude(CustomerDashboard route) {

        customerService.persistCustomer(route.customer());
        onConclude.accept(route);
    }
}
