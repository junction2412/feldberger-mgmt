package de.code.junction.feldberger.mgmt.presentation.components.common;

import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;
import de.code.junction.feldberger.mgmt.presentation.view.main.menu.MainMenuViewModel;

public class Session {

    private final int userId;
    private final CustomerOverviewModel customerOverviewModel;
    private final MainMenuViewModel mainMenuViewModel;

    public Session(int userId) {

        this.userId = userId;

        customerOverviewModel = new CustomerOverviewModel(
                userId,
                ""
        );

        mainMenuViewModel = new MainMenuViewModel(
                userId,
                "",
                null
        );
    }

    public int getUserId() {

        return userId;
    }

    public CustomerOverviewModel getCustomerOverviewModel() {

        return customerOverviewModel;
    }

    public MainMenuViewModel getMainMenuViewModel() {

        return mainMenuViewModel;
    }
}
