package de.code.junction.feldberger.mgmt.data.converter;

import de.code.junction.feldberger.mgmt.data.encryption.EncryptionService;
import de.code.junction.feldberger.mgmt.data.encryption.EncryptionServiceFactory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class StringColumnEncryptor implements AttributeConverter<String, String> {

    private final EncryptionService<String, String> encryptionService;

    public StringColumnEncryptor() {
        this.encryptionService = EncryptionServiceFactory.getInstance().createStringService();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {

        if (attribute == null) return null;

        return encryptionService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        if (dbData == null) return null;

        return encryptionService.decrypt(dbData);
    }
}
