package de.code.junction.feldberger.mgmt.presentation.navigation;

import java.util.HashMap;

public record Route<T extends RouteName>(T name, HashMap<String, Object> cache) {

    public Route(T name) {

        this(name, new HashMap<>());
    }
}
