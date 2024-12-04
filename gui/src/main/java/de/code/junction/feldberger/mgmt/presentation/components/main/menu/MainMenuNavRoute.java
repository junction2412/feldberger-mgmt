package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;

public sealed interface MainMenuNavRoute {

    enum Subview {
        NONE(null),
        CUSTOMERS("enum.main.menu.nav.route.customers"),
        TRANSACTIONS("enum.main.menu.nav.route.transactions"),
        ;

        private final String labelKey;

        Subview(String labelKey) {

            this.labelKey = labelKey;
        }

        public String getLabelKey() {

            return labelKey;
        }
    }

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
