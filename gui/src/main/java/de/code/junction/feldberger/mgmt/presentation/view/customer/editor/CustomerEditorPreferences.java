package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class CustomerEditorPreferences {

    private CustomerEditorPreferences() {
    }

    public static final UIPreference EDITED_CUSTOMER_ID = new UIPreference(
            "customer-editor",
            "customer.id",
            null,
            Integer.class,
            PreferenceLifetime.VIEW,
            "The id of the user being edited. Nullable if nothing is being edited."
    );
}
