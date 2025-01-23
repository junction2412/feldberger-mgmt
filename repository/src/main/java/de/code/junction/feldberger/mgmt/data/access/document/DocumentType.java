package de.code.junction.feldberger.mgmt.data.access.document;

public enum DocumentType {
    QUOTATION(1, "enum.document.type.quotation"),
    PACKING_SLIP(2, "enum.document.type.packing.slip"),
    INVOICE(3, "enum.document.type.invoice"),
    ;

    private final int id;
    private final String translationKey;

    DocumentType(int id, String translationKey) {

        this.id = id;
        this.translationKey = translationKey;
    }

    public int getId() {

        return id;
    }

    public String getTranslationKey() {

        return translationKey;
    }
}
