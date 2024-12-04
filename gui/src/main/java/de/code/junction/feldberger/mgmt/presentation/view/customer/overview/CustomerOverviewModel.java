package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerOverviewModel {

    private final IntegerProperty selectedCustomerId;
    private final StringProperty customerFilter;

    public CustomerOverviewModel(int customerID, String customerFilter) {

        this.selectedCustomerId = new SimpleIntegerProperty(customerID);
        this.customerFilter = new SimpleStringProperty(customerFilter);
    }

    public int getSelectedCustomerId() {

        return selectedCustomerId.get();
    }

    public IntegerProperty selectedCustomerIdProperty() {

        return selectedCustomerId;
    }

    public void setSelectedCustomerId(int selectedCustomerId) {

        this.selectedCustomerId.set(selectedCustomerId);
    }

    public String getCustomerFilter() {

        return customerFilter.get();
    }

    public StringProperty customerFilterProperty() {

        return customerFilter;
    }

    public void setCustomerFilter(String customerFilter) {

        this.customerFilter.set(customerFilter);
    }
}
