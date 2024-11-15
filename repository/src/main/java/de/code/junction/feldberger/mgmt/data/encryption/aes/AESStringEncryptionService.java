package de.code.junction.feldberger.mgmt.data.encryption.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESStringEncryptionService implements AESEncryptionService<String, String> {

    private final SecretKey secretKey;

    public AESStringEncryptionService(String password) {

        secretKey = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), "AES");
    }


    @Override
    public String encrypt(String clear) {
        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            final var encryptedBytes = cipher.doFinal(clear.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String encrypted) {

        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            var encryptedBytes = Base64.getDecoder().decode(encrypted);
            var decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
