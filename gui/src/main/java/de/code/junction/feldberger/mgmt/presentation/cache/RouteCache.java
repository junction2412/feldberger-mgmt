package de.code.junction.feldberger.mgmt.presentation.cache;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class RouteCache {

    @JsonProperty("name")
    private final String routeName;

    @JsonProperty("cache")
    private final Map<String, Object> cache;

    @JsonProperty("route")
    private RouteCache route;

    public RouteCache(String routeName,
                      Map<String, Object> cache,
                      RouteCache route) {

        this.routeName = routeName;
        this.cache = cache;
        this.route = route;
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
}
