package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class CustomerOverviewPreferences {

    public static final String SCOPE = "customer-overview";

    private CustomerOverviewPreferences() {
    }

    public static final UIPreference FILTER = new UIPreference(
            SCOPE,
            "filter.text",
            "",
            String.class,
            PreferenceLifetime.SCENE,
            "The applied customer filter."
    );

    public static final UIPreference SELECTED_CUSTOMER_ID = new UIPreference(
            SCOPE,
            "customers.selectedItem",
            0,
            Integer.class,
            PreferenceLifetime.SCENE,
            "Selected customer id."
    );
}
