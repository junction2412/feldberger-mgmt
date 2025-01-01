package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class CustomerEditorPreferences {

    public static final String SCOPE = "customer-editor";

    private CustomerEditorPreferences() {
    }

    public static final UIPreference EDITED_CUSTOMER_ID = new UIPreference(
            SCOPE,
            "customer.id",
            0,
            Integer.class,
            PreferenceLifetime.VIEW,
            "The id of the user being edited."
    );
}
