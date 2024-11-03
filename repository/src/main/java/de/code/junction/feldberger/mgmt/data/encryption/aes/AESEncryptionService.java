package de.code.junction.feldberger.mgmt.data.encryption.aes;

import de.code.junction.feldberger.mgmt.data.encryption.EncryptionService;

public interface AESEncryptionService<E, D> extends EncryptionService<E, D> {

    String TRANSFORM = "AES/ECB/PKCS5Padding";
}
