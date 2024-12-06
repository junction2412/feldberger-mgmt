package de.code.junction.feldberger.mgmt.presentation.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.code.junction.feldberger.mgmt.data.AppConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * A class to be used once every login attempt to recreate the last session state
 *
 * @author J. Murray
 */
public class RouteRecreationQueue {

    private static RouteRecreationQueue instance;

    private RouteCache currentRouteCache;

    private RouteRecreationQueue(int userId) {

        currentRouteCache = buildCache(userId);
    }

    public static RouteRecreationQueue getInstance(int userId) {

        instance = new RouteRecreationQueue(userId);

        return getInstance();
    }

    public static RouteRecreationQueue getInstance() {

        return instance;
    }

    private static RouteCache buildCache(int userId) {

        if (!Files.exists(AppConstants.SESSION_CACHE))
            return newSessionCache(userId);

        final String json;

        try {
            json = Files.readString(AppConstants.SESSION_CACHE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final var sessionCache = new JSONObject(json);
        final var sessions = sessionCache.getJSONArray("sessions");


        for (final Object object : sessions) {
            final var session = (JSONObject) object;

            if (session.getInt("userId") != userId) continue;

            final var route = session.getJSONObject("route");
            final var objectMapper = new ObjectMapper();

            try {
                return objectMapper.readValue(route.toString(), RouteCache.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("could not instantiate the route cache queue");
    }

    private static RouteCache newSessionCache(int userId) {

        try {
            Files.createDirectories(AppConstants.CACHE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final var sessionCache = new JSONObject();
        final var sessions = new JSONArray();
        sessionCache.put("session", sessions);
        final var session = new JSONObject();
        sessions.put(session);
        session.put("userId", userId);

        final var route = new RouteCache("main.menu");
        final var objectMapper = new ObjectMapper();

        final String routeJson;

        try {
            routeJson = objectMapper.writeValueAsString(route);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        session.put("route", new JSONObject(routeJson));

        try {
            Files.writeString(AppConstants.SESSION_CACHE, sessionCache.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return route;
    }

    public Optional<RouteCache> next() {

        currentRouteCache = currentRouteCache.getRoute();

        return Optional.ofNullable(currentRouteCache);
    }
}
