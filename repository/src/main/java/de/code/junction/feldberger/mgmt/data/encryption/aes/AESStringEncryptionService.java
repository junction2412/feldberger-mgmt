package de.code.junction.feldberger.mgmt.data.encryption.aes;

import java.util.Base64;

public class AESStringEncryptionService implements AESEncryptionService<String, String> {

    private final AESByteArrayEncryptionService encryptionService;

    public AESStringEncryptionService(AESByteArrayEncryptionService encryptionService) {

        this.encryptionService = encryptionService;
    }


    @Override
    public String encrypt(String clear) {

        final var encryptedBytes = encryptionService.encrypt(clear.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encrypted) {

        var encryptedBytes = Base64.getDecoder().decode(encrypted);
        return new String(encryptionService.decrypt(encryptedBytes));
    }
}
