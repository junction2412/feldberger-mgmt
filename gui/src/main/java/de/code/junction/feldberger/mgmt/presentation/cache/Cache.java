package de.code.junction.feldberger.mgmt.presentation.cache;

import de.code.junction.feldberger.mgmt.data.AppConstants;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.RouteName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

public final class Cache {

    private Cache() {
    }

    public static <R extends RouteName> Stack<Route<R>> getScopeRoutes(int userId, String scopeName) {

        final var routes = new Stack<Route<R>>();

        try {
            final var sessionCache = new JSONObject(Files.readString(AppConstants.SESSION_CACHE));
            final var session = findSession(userId, sessionCache);
            final var scope = findScope(session, scopeName);
            final var _routes = scope.optJSONArray("routes", new JSONArray());

            for (Object _route : _routes) {
                final var route = (JSONObject) _route;

                final R name = getName(scopeName, route);
                final var _cache = new HashMap<>(route.getJSONObject("cache").toMap());

                routes.push(new Route<R>(
                        name,
                        _cache
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return routes;
    }

    private static <R extends RouteName> R getName(String scopeName, JSONObject route) {

        return (R) switch (scopeName) {
            case "application" -> ApplicationRoute.byName(route.getString("name"));
            case "main.menu" -> MainMenuRoute.byName(route.getString("name"));
            default -> throw new IllegalStateException("Cache corruption detected in scope \"" + scopeName + "\" at " +
                    route);
        };
    }

    private static JSONObject findSession(int userId, JSONObject sessionCache) {

        final var sessions = sessionCache.getJSONArray("sessions");

        Optional<JSONObject> optionalSession = Optional.empty();
        for (Object _session : sessions) {
            final var session = (JSONObject) _session;

            final int _userId = session.getInt("userId");

            if (userId != _userId) continue;

            optionalSession = Optional.of(session);
        }

        return optionalSession.orElseGet(JSONObject::new);
    }

    private static JSONObject findScope(JSONObject session, String name) {

        final var scopes = session.optJSONArray("scopes", new JSONArray());

        Optional<JSONObject> optionalScope = Optional.empty();
        for (Object _scope : scopes) {
            final var scope = (JSONObject) _scope;

            if (!scope.getString("name").equals(name)) continue;

            optionalScope = Optional.of(scope);
        }

        return optionalScope.orElseGet(JSONObject::new);
    }
}
