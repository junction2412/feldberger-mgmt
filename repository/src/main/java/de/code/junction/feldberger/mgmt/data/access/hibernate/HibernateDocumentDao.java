package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.document.Document;
import de.code.junction.feldberger.mgmt.data.access.document.DocumentDataAccessObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class HibernateDocumentDao
        extends HibernateDao
        implements DocumentDataAccessObject {

    public HibernateDocumentDao(SessionFactory sessionFactory) {

        super(sessionFactory);
    }

    @Override
    public Optional<Document> findById(Integer id) {

        final Document document = sessionFactory.fromSession(session -> session.find(Document.class, id));
        return Optional.ofNullable(document);
    }

    @Override
    public List<Document> getAll() {

        final var query = new CriteriaDefinition<>(sessionFactory, Document.class) {
            {
                from(Document.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }

    @Override
    public void persistDocument(Document document) {

        final Consumer<Session> sessionConsumer = (document.getId() == 0)
                ? session -> session.persist(document)
                : session -> session.merge(document);

        sessionFactory.inTransaction(sessionConsumer);
    }

    @Override
    public void removeDocument(Document document) {

        if (document.getId() == 0) return;

        sessionFactory.inTransaction(session -> session.remove(document));
    }
}
