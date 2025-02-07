package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import static de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerSaveResult.*;

public class CustomerSavingService extends Service<CustomerSaveResult> {

    private final CustomerService customerService;
    private final ObjectProperty<Customer> customer;

    public CustomerSavingService(CustomerService customerService) {

        this.customerService = customerService;
        customer = new SimpleObjectProperty<>();
    }

    @Override
    protected Task<CustomerSaveResult> createTask() {

        final var customer = getCustomer();
        final var customerId = customer.getId();
        final var address = customer.getAddress();

        return new Task<>() {

            @Override
            protected CustomerSaveResult call() {

                final var optionalCustomer = customerService.findCustomerByIdNo(customer.getIdNo());

                if (optionalCustomer.isPresent()) {
                    final var customerFromDb = optionalCustomer.get();
                    final var isSameRecord = customerId.equals(customerFromDb.getId());

                    if (!isSameRecord)
                        return Failure.IDNO_CONSTRAINT_VIOLATION;
                }

                final var isNewCustomer = customerId == 0;

                customerService.persistCustomer(customer);

                return (isNewCustomer)
                        ? new Created(customer.getId())
                        : UPDATED;
            }
        };
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }
}
