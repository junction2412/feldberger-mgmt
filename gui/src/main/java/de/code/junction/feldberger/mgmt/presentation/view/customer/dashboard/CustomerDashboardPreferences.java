package de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class CustomerDashboardPreferences {

    public static final String SCOPE = "customer-dashboard";

    private CustomerDashboardPreferences() {
    }

    public static final UIPreference SHOWN_CUSTOMER_ID = new UIPreference(
            SCOPE,
            "customer.id",
            0,
            Integer.class,
            PreferenceLifetime.VIEW,
            ""
    );

    public static final UIPreference SELECTED_TRANSACTION_ID = new UIPreference(
            SCOPE,
            "transactions.selectedItem",
            0,
            Integer.class,
            PreferenceLifetime.VIEW,
            ""
    );
}
