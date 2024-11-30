package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.PassthroughLifecycle;

import java.util.Optional;
import java.util.function.Consumer;

public class CustomerEditorCustomerDashboardTransitionLifecycle implements PassthroughLifecycle<Customer> {

    private final CustomerService customerService;
    private final Messenger messenger;
    private final Consumer<Customer> onConclude;

    public CustomerEditorCustomerDashboardTransitionLifecycle(CustomerService customerService,
                                                              Messenger messenger,
                                                              Consumer<Customer> onConclude) {

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
    public void conclude(Customer customer) {

        customerService.persistCustomer(customer);
        onConclude.accept(customer);
    }
}
