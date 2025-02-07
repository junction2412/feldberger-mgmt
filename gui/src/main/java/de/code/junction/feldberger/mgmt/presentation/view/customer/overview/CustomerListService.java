package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.stream.Collectors;

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

                return customerDao.getActiveCustomers().stream()
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
            }
        };
    }
}
