package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CustomerListService extends Service<ObservableList<Customer>> {

    private final CustomerDataAccessObject customerDao;

    public CustomerListService(CustomerDataAccessObject customerDao) {

        this.customerDao = customerDao;
    }

    @Override
    protected Task<ObservableList<Customer>> createTask() {

        return new Task<>() {

            @Override
            protected ObservableList<Customer> call() {

                return FXCollections.observableList(customerDao.getActiveCustomers());
            }
        };
    }
}
