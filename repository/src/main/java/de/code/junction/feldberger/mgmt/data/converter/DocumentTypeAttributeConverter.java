package de.code.junction.feldberger.mgmt.data.converter;

import de.code.junction.feldberger.mgmt.data.access.document.DocumentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class DocumentTypeAttributeConverter implements AttributeConverter<DocumentType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DocumentType documentType) {
        return documentType.getId();
    }

    @Override
    public DocumentType convertToEntityAttribute(Integer id) {

        return Stream.of(DocumentType.values())
                .filter(type -> type.getId() == id)
                .findFirst()
                .orElseThrow();
    }
}
