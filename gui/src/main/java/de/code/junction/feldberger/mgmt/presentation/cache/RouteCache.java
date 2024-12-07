package de.code.junction.feldberger.mgmt.presentation.cache;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RouteCache {

    private final String routeName;
    private final Map<String, Object> cache;
    private RouteCache route;

    public RouteCache(String routeName,
                      Map<String, Object> cache,
                      RouteCache route) {

        this.routeName = routeName;
        this.cache = cache;
        this.route = route;
    }

    public RouteCache(JSONObject route) {

        this(
                route.getString("name"),

                route.has("cache")
                        ? route.getJSONObject("cache").toMap()
                        : new HashMap<>(),

                route.has("route")
                        ? new RouteCache(route.getJSONObject("route"))
                        : null
        );
    }

    public RouteCache(String routeName) {

        this(
                routeName,
                new HashMap<>(),
                null
        );
    }

    public String getRouteName() {

        return routeName;
    }

    public Map<String, Object> getCache() {

        return cache;
    }

    public RouteCache getRoute() {

        return route;
    }

    public void setRoute(RouteCache route) {

        this.route = route;
    }

    public String toJson() {

        final var route = new JSONObject();

        route.put("name", routeName);

        if (!cache.isEmpty())
            route.put("cache", new JSONObject(cache));

        if (this.route != null)
            route.put("route", new JSONObject(this.route.toJson()));

        return route.toString();
    }

    ;
}
