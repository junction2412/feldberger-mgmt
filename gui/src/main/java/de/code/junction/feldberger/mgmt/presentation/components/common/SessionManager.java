package de.code.junction.feldberger.mgmt.presentation.components.common;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static SessionManager instance;

    private final Map<Integer, Session> runtimeCache;

    private SessionManager() {

        runtimeCache = new HashMap<>();
    }

    public static SessionManager getInstance() {

        if (instance == null)
            instance = new SessionManager();

        return instance;
    }

    public Session retrieveSession(int userId) {

        return runtimeCache.computeIfAbsent(userId, Session::new);
    }
}
