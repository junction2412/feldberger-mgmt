package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;

public sealed interface MainMenuRoute {

    record CustomerOverview() implements MainMenuRoute {
    }

    record CustomerEditor(MainMenuRoute backRoute, Customer customer) implements MainMenuRoute {

        public CustomerEditor() {
            this(new CustomerOverview(), new Customer());
        }
    }

    record CustomerDashboard(Customer customer) implements MainMenuRoute {
    }
}
