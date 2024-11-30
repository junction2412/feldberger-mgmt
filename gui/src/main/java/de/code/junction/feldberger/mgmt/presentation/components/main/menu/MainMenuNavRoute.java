package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;

public sealed interface MainMenuNavRoute {

    enum Subview implements MainMenuNavRoute {
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

    record CustomerEditor(Customer customer) implements MainMenuNavRoute {
    }

    record CustomerDashboard(Customer customer) implements MainMenuNavRoute {
    }
}
