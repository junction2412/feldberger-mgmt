package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.service.CustomerService;

import static de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerSaveResult.UPDATED;

public class SaveCustomer {

    private final CustomerService customerService;

    public SaveCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerSaveResult save(Customer customer) {

        final var customerId = customer.getId();
        final var optionalCustomer = customerService.findCustomerByIdNo(customer.getIdNo());

        if (optionalCustomer.isPresent()) {
            final var customerFromDb = optionalCustomer.get();
            final var isSameRecord = customerId.equals(customerFromDb.getId());

            if (!isSameRecord)
                return CustomerSaveResult.Failure.IDNO_CONSTRAINT_VIOLATION;
        }

        final var isNewCustomer = customerId == 0;

        customerService.persistCustomer(customer);

        return (isNewCustomer)
                ? new CustomerSaveResult.Created(customer.getId())
                : UPDATED;
    }
}
