package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

public sealed interface MainMenuRoute {

    record CustomerOverview(int selectedCustomerId) implements MainMenuRoute {

        public CustomerOverview() {

            this(0);
        }
    }

    record CustomerEditor(int customerId, boolean fromDashboard) implements MainMenuRoute {

        CustomerEditor(int customerId) {

            this(customerId, false);
        }

        /**
         * Default constructor used to edit a new customer record.
         */
        public CustomerEditor() {

            this(0, false);
        }
    }

    record CustomerDashboard(int customerId, int selectedTransactionId) implements MainMenuRoute {

        public CustomerDashboard(int customerId) {

            this(customerId, 0);
        }
    }
}
