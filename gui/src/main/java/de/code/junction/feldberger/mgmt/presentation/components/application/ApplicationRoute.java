package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.presentation.navigation.RouteName;

import java.util.Arrays;

public enum ApplicationRoute implements RouteName {

    REGISTRATION("registration"),
    LOGIN("login"),
    MAIN_MENU("main.menu"),
    ;

    private final String name;

    ApplicationRoute(String name) {

        this.name = name;
    }

    @Override
    public String string() {

        return name;
    }

    public static ApplicationRoute byName(String name) {

        return Arrays.stream(ApplicationRoute.values())
                .filter(route -> route.name.equals(name))
                .findFirst()
                .orElseThrow();
    }
}
