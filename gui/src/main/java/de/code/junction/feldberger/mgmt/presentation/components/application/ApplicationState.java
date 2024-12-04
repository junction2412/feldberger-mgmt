package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;
import javafx.application.Platform;

public class ApplicationState {

    private static ApplicationState instance;

    private final CustomerOverviewModel customerOverviewModel;

    private ApplicationState() {

        customerOverviewModel = new CustomerOverviewModel(0, "");
    }

    public static ApplicationState getInstance() {

        if (instance == null)
            instance = new ApplicationState();

        return instance;
    }

    public CustomerOverviewModel getCustomerOverviewModel() {

        return customerOverviewModel;
    }

    public void forget() {

        Platform.runLater(() -> {

            customerOverviewModel.setSelectedCustomerId(0);
            customerOverviewModel.setCustomerFilter("");
        });
    }
}
