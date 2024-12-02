package de.code.junction.feldberger.mgmt.data.encryption.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESByteArrayEncryptionService implements AESEncryptionService<byte[], byte[]> {

    private final SecretKey secretKey;

    public AESByteArrayEncryptionService(String password) {

        secretKey = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), "AES");
    }

    @Override
    public byte[] encrypt(byte[] clear) {

        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(clear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] decrypt(byte[] encrypted) {

        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
