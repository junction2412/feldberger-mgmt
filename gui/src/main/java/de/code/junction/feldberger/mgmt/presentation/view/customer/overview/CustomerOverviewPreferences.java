package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class CustomerOverviewPreferences {

    private CustomerOverviewPreferences() {
    }

    public static final UIPreference FILTER = new UIPreference(
            "customer-overview",
            "filter.text",
            "",
            String.class,
            PreferenceLifetime.SCENE,
            "The applied customer filter."
    );

    public static final UIPreference SELECTED_CUSTOMER_ID = new UIPreference(
            "customer-overview",
            "customers.selectedItem",
            0,
            Integer.class,
            PreferenceLifetime.SCENE,
            "Selected customer id."
    );
}
