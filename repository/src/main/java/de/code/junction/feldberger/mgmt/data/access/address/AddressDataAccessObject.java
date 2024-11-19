package de.code.junction.feldberger.mgmt.data.access.address;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;

import java.util.Optional;

/**
 * The {@link DataAccessObject} definition for {@link Address}es.
 *
 * @author J. Murray
 */
public interface AddressDataAccessObject extends DataAccessObject<Integer, Address> {

    /**
     * Insert or update an address.
     *
     * @param address address
     */
    void persistAddress(Address address);

    /**
     * Finds a unique address by its fields.
     *
     * @return unique address
     */
    Optional<Address> findUniqueAddress(final String countryCode,
                                        final String postalCode,
                                        final String city,
                                        final String street,
                                        final String streetNumber,
                                        final String suffix);
}
