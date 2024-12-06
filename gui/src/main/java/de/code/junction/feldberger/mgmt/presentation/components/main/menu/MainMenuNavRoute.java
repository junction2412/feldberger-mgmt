package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;

public sealed interface MainMenuNavRoute {

    record CustomerOverview(int customerId) implements MainMenuNavRoute {

        public CustomerOverview() {

            this(0);
        }

        public CustomerOverview(Customer customer) {

            this(customer.getId());
        }
    }

    record CustomerEditor(Customer customer, BackAction backAction) implements MainMenuNavRoute {

        enum BackAction {
            OVERVIEW,
            DASHBOARD,
        }
    }

    record CustomerDashboard(Customer customer) implements MainMenuNavRoute {
    }
}
