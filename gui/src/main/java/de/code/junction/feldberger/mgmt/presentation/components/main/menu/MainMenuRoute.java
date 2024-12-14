package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.presentation.navigation.RouteName;

import java.util.Arrays;

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

    public static MainMenuRoute byName(String name) {

        return Arrays.stream(values())
                .filter(route -> route.name.equals(name))
                .findFirst()
                .orElseThrow();
    }
}
