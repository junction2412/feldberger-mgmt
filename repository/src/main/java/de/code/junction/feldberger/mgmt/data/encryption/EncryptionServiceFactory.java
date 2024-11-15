package de.code.junction.feldberger.mgmt.data.encryption;

import de.code.junction.feldberger.mgmt.data.encryption.aes.AESStringEncryptionService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Properties;

public class EncryptionServiceFactory {

    public static final EncryptionServiceFactory INSTANCE = new EncryptionServiceFactory();
    private final String password;

    public EncryptionServiceFactory() {

        final var appEnv = loadAppEnv();
        password = appEnv.getProperty("CORE_SECRET_KEY");
    }

    private static Properties loadAppEnv() {

        final var userHome = Path.of(System.getProperty("user.home"));
        final var appHome = userHome.resolve(".feldberger-mgmt").toAbsolutePath();
        final var env = appHome.resolve(".encryption.properties");
        final var appEnv = new Properties();

        if (!Files.exists(env)) {
            final byte[] bytes = new byte[16];
            new SecureRandom().nextBytes(bytes);

            final StringBuilder builder = new StringBuilder();

            for (final byte b : bytes)
                builder.append(String.format("%02x", b));

            appEnv.put("CORE_SECRET_KEY", builder.toString());

            try (final OutputStream outputStream = Files.newOutputStream(env)) {
                appEnv.store(outputStream, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            try (final InputStream inputStream = Files.newInputStream(env)) {
                appEnv.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return appEnv;
    }

    public EncryptionService<String, String> createService() {

        return new AESStringEncryptionService(password);
    }
}
