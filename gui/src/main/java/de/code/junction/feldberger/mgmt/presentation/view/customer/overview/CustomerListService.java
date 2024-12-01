package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class CustomerListService extends Service<List<Customer>> {

    private final CustomerDataAccessObject customerDao;
    private final StringProperty nameOrCompanyName;

    public CustomerListService(CustomerDataAccessObject customerDao) {

        this.customerDao = customerDao;
        this.nameOrCompanyName = new SimpleStringProperty("");
    }

    @Override
    protected Task<List<Customer>> createTask() {

        return new Task<>() {

            @Override
            protected List<Customer> call() throws Exception {

                // TODO: fix filter
                final String nameOrCompanyName = getNameOrCompanyName();

                if (nameOrCompanyName.isBlank())
                    return customerDao.getActiveCustomers();

                return customerDao.getByNameOrCompanyName(nameOrCompanyName);
            }
        };
    }

    public String getNameOrCompanyName() {

        return nameOrCompanyName.get();
    }

    public StringProperty nameOrCompanyNameProperty() {

        return nameOrCompanyName;
    }
}
