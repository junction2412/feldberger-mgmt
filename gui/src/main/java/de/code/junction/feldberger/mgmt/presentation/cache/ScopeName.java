package de.code.junction.feldberger.mgmt.presentation.cache;

import java.util.Arrays;

/**
 * An enum to ensure I never stringify constant values that translate in a specific manner.
 */
public enum ScopeName {

    APPLICATION("application"),
    MAIN_MENU("main.menu"),
    ;

    final String name;

    ScopeName(String name) {
        this.name = name;
    }

    public String string() {

        return name;
    }

    public static ScopeName byName(String name) {

        return Arrays.stream(values())
                .filter(scope -> scope.name.equals(name))
                .findFirst()
                .orElseThrow();
    }
}
