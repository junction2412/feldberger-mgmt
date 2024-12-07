package de.code.junction.feldberger.mgmt.data;

import java.nio.file.Path;

public class AppConstants {

    private AppConstants() {
    }

    public static final Path APPLICATION_HOME = Path.of(System.getProperty("user.home")).resolve(".feldberger-mgmt");
    public static final Path ENCRYPTION_PROPERTIES = APPLICATION_HOME.resolve(".encryption.properties");
    public static final Path CACHE = APPLICATION_HOME.resolve("cache");
    public static final Path SESSION_CACHE = CACHE.resolve("session.json");
}
