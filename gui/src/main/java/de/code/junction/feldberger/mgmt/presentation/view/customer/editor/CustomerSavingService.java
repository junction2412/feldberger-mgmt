package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CustomerSavingService extends Service<CustomerSaveResult> {

    private final SaveCustomer saveCustomer;
    private final ObjectProperty<Customer> customer;

    public CustomerSavingService(SaveCustomer saveCustomer) {

        this.saveCustomer = saveCustomer;
        customer = new SimpleObjectProperty<>();
    }

    @Override
    protected Task<CustomerSaveResult> createTask() {

        final var customer = getCustomer();

        return new Task<>() {

            @Override
            protected CustomerSaveResult call() {
                return saveCustomer.save(customer);
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
