package de.code.junction.feldberger.mgmt.data.converter;

import de.code.junction.feldberger.mgmt.data.encryption.EncryptionService;
import de.code.junction.feldberger.mgmt.data.encryption.EncryptionServiceFactory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ByteArrayColumnEncryptor implements AttributeConverter<byte[], byte[]> {

    private final EncryptionService<byte[], byte[]> encryptionService;

    public ByteArrayColumnEncryptor() {

        this.encryptionService = EncryptionServiceFactory.getInstance().createByteArrayService();
    }

    @Override
    public byte[] convertToDatabaseColumn(byte[] bytes) {

        if (bytes == null) return null;

        return encryptionService.encrypt(bytes);
    }

    @Override
    public byte[] convertToEntityAttribute(byte[] bytes) {

        if (bytes == null) return null;

        return encryptionService.decrypt(bytes);
    }
}
