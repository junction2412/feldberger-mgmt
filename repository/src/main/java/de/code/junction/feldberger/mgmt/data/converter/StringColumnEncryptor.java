package de.code.junction.feldberger.mgmt.data.converter;

import de.code.junction.feldberger.mgmt.data.encryption.EncryptionService;
import de.code.junction.feldberger.mgmt.data.encryption.EncryptionServiceFactory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class StringColumnEncryptor implements AttributeConverter<String, String> {

    private final EncryptionService<String, String> encryptionService;

    public StringColumnEncryptor() {

        this.encryptionService = EncryptionServiceFactory.INSTANCE.createService();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {

        return encryptionService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        return encryptionService.decrypt(dbData);
    }
}
