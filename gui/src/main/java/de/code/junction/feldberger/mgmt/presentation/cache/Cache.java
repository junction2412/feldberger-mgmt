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
import java.util.Stack;
import java.util.stream.StreamSupport;

public final class Cache {

    private Cache() {
    }

    public static <R extends RouteName> Stack<Route<R>> getScopeRoutes(int userId,
                                                                       ScopeName scopeName) {

        final var routes = new Stack<Route<R>>();

        if (Files.exists(AppConstants.SESSION_CACHE))
            fillRoutesStack(userId, scopeName, routes);

        return routes;
    }

    private static <R extends RouteName> void fillRoutesStack(int userId,
                                                              ScopeName scopeName,
                                                              Stack<Route<R>> routes) {

        try {
            final var sessionCache = new JSONObject(Files.readString(AppConstants.SESSION_CACHE));
            final var session = findSession(userId, sessionCache);
            final var scope = findScope(session, scopeName);
            final var _routes = scope.optJSONArray("routes", new JSONArray());

            for (Object _route : _routes) {
                final var route = (JSONObject) _route;

                final R name = getName(scopeName, route);
                final var _cache = new HashMap<>(route.getJSONObject("cache").toMap());

                routes.push(new Route<>(name, _cache));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <R extends RouteName> R getName(ScopeName scopeName, JSONObject route) {

        return (R) switch (scopeName) {
            case APPLICATION -> ApplicationRoute.byName(route.getString("name"));
            case MAIN_MENU -> MainMenuRoute.byName(route.getString("name"));
        };
    }

    private static JSONObject findSession(int userId, JSONObject sessionCache) {

        final var sessions = sessionCache.getJSONArray("sessions");

        return StreamSupport.stream(sessions.spliterator(), false)
                .map(object -> (JSONObject) object)
                .filter(session -> session.getInt("userId") == userId)
                .findFirst()
                .orElseGet(JSONObject::new);
    }

    private static JSONObject findScope(JSONObject session, ScopeName name) {

        final var scopes = session.optJSONArray("scopes", new JSONArray());

        return StreamSupport.stream(scopes.spliterator(), false)
                .map(object -> (JSONObject) object)
                .filter(scope -> name.string().equals(scope.getString("name")))
                .findFirst()
                .orElseGet(JSONObject::new);
    }
}
