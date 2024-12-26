package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CustomerListService extends Service<ObservableList<CustomerItemViewModel>> {

    private final CustomerDataAccessObject customerDao;

    public CustomerListService(CustomerDataAccessObject customerDao) {

        this.customerDao = customerDao;
    }

    @Override
    protected Task<ObservableList<CustomerItemViewModel>> createTask() {

        return new Task<>() {

            @Override
            protected ObservableList<CustomerItemViewModel> call() {

                final var viewModels = customerDao.getActiveCustomers().stream()
                        .map(customer -> {

                            final var nameOrCompanyName = (customer.getCompanyName().isEmpty())
                                    ? customer.getFullName()
                                    : customer.getCompanyName();

                            return new CustomerItemViewModel(customer.getId(), customer.getIdNo(), nameOrCompanyName);
                        }).toList();

                return FXCollections.observableList(viewModels);
            }
        };
    }
}
