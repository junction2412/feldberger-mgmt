package de.code.junction.feldberger.mgmt.data.access.hibernate;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.address.Address_;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;
import java.util.Optional;

public class HibernateAddressDao
        extends HibernateDao
        implements AddressDataAccessObject {

    public HibernateAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void persistAddress(Address address) {

        sessionFactory.inTransaction(session -> {

            if (address.getId() == 0)
                session.persist(address);
            else
                session.merge(address);
        });
    }

    @Override
    public Optional<Address> findUniqueAddress(final String countryCode,
                                               final String postalCode,
                                               final String city,
                                               final String street,
                                               final String streetNumber,
                                               final String suffix) {

        final var query = new CriteriaDefinition<>(sessionFactory, Address.class) {
            {
                final JpaRoot<Address> address = from(Address.class);
                final JpaPredicate countryCodeEqual = equal(address.get(Address_.countryCode), countryCode);
                final JpaPredicate postalCodeEqual = equal(address.get(Address_.postalCode), postalCode);
                final JpaPredicate cityEqual = equal(address.get(Address_.city), city);
                final JpaPredicate streetEqual = equal(address.get(Address_.street), street);
                final JpaPredicate streetNumberEqual = equal(address.get(Address_.streetNumber), streetNumber);
                final JpaPredicate suffixEqual = equal(address.get(Address_.suffix), suffix);

                where(and(countryCodeEqual, postalCodeEqual, cityEqual, streetEqual, streetNumberEqual, suffixEqual));
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).uniqueResultOptional());
    }

    @Override
    public Optional<Address> findById(Integer id) {

        return Optional.ofNullable(sessionFactory.fromSession(session -> session.find(Address.class, id)));
    }

    @Override
    public List<Address> getAll() {

        final var query = new CriteriaDefinition<>(sessionFactory, Address.class) {
            {
                from(Address.class);
            }
        };

        return sessionFactory.fromSession(session -> session.createSelectionQuery(query).list());
    }
}
