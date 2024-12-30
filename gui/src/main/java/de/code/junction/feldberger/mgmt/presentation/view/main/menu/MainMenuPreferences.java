package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceLifetime;
import de.code.junction.feldberger.mgmt.presentation.preferences.UIPreference;

public final class MainMenuPreferences {

    private MainMenuPreferences() {
    }

    public static final UIPreference SELECTED_SUBVIEW = new UIPreference(
            "main-menu",
            "subviews.selectedItem",
            null,
            String.class,
            PreferenceLifetime.SCENE,
            "The subview selected by the user."
    );
}
