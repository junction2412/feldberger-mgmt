package de.code.junction.feldberger.mgmt.data.access.customer;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;

import java.util.List;
import java.util.Optional;

/**
 * The {@link DataAccessObject} definition for {@link Customer}s.
 *
 * @author J. Murray
 */
public interface CustomerDataAccessObject extends DataAccessObject<Integer, Customer> {

    /**
     * Retrieve a list of all archived customers.
     *
     * @return archived customers
     */
    List<Customer> getArchivedCustomers();

    /**
     * Retrieve a list of all active customers.
     *
     * @return active customers
     */
    List<Customer> getActiveCustomers();

    /**
     * Insert or update a customer.
     *
     * @param customer customer
     */
    void persistCustomer(Customer customer);

    Optional<Customer> findCustomerByIdNo(String idNo);

    List<Customer> getByNameOrCompanyName(String nameOrCompanyName);
}
