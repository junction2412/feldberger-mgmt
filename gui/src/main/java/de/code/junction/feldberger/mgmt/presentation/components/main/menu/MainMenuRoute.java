package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.presentation.navigation.RouteName;

public enum MainMenuRoute implements RouteName {

    CUSTOMER_OVERVIEW("customer.overview"),
    CUSTOMER_EDITOR("customer.editor"),
    CUSTOMER_DASHBOARD("customer.dashboard"),
    ;

    private final String name;

    MainMenuRoute(String name) {

        this.name = name;
    }

    @Override
    public String string() {

        return name;
    }
}
