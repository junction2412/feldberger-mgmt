package de.code.junction.feldberger.mgmt.data.access.document;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;

public interface DocumentDataAccessObject extends DataAccessObject<Integer, Document> {

    void persistDocument(Document document);

    void removeDocument(Document document);
}
