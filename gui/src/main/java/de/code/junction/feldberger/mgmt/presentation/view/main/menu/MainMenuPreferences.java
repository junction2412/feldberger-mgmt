package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class MainMenuPreferences {

    public static final String SCOPE = "main-menu";

    private MainMenuPreferences() {
    }

    public static final UIPreference SELECTED_SUBVIEW = new UIPreference(
            SCOPE,
            "subviews.selectedItem",
            null,
            String.class,
            PreferenceLifetime.SCENE,
            "The subview selected by the user."
    );
}
