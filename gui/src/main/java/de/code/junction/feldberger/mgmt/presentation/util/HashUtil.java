package de.code.junction.feldberger.mgmt.presentation.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * HashUtil is just a collection of crypto utility functions such as sha256, byte array to hex string and so on.
 *
 * @author J. Murray
 */
public class HashUtil {

    private HashUtil() {
    }

    /**
     * Converts a byte array to a hex string.
     *
     * @param bytes byte array
     * @return hex string
     */
    public static String toHexString(byte[] bytes) {

        final StringBuilder builder = new StringBuilder();

        for (byte b : bytes)
            builder.append(String.format("%02x", b));

        return builder.toString();
    }

    /**
     * Generates a random salt of the given byte size (as hex string).
     *
     * @param byteSize size of the salt
     * @return salt (as hex string)
     */
    public static String salt(int byteSize) {

        final byte[] bytes = new byte[byteSize];
        new SecureRandom().nextBytes(bytes);

        return toHexString(bytes);
    }

    /**
     * Generates a default salt of 16 bytes.
     *
     * @return salt (as hex string)
     * @see HashUtil#salt(int)
     */
    public static String salt() {

        return salt(16);
    }

    /**
     * Apply SHA-256 on the given input string.
     *
     * @param input input string
     * @return SHA-256 hash (as hex string)
     */
    public static String sha256(String input) {

        final MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // almost impossible
            throw new RuntimeException(e);
        }

        return toHexString(messageDigest.digest(input.getBytes()));
    }

    /**
     * Streamlined password hashing. Just pass the password and the salt. This method handles the remaining steps :)
     *
     * @param password password to be hashed
     * @param salt     salt to be hashed with
     * @return SHA-256 hashed password (as hex string)
     */
    public static String hashPassword(String password, String salt) {

        return sha256(password + salt);
    }
}
