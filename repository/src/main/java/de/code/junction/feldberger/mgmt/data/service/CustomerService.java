package de.code.junction.feldberger.mgmt.data.service;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.address.AddressDataAccessObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.customer.CustomerDataAccessObject;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private CustomerDataAccessObject customerDao;
    private AddressDataAccessObject addressDao;

    public CustomerService(CustomerDataAccessObject customerDao, AddressDataAccessObject addressDao) {

        this.customerDao = customerDao;
        this.addressDao = addressDao;
    }

    public CustomerDataAccessObject getCustomerDao() {

        return customerDao;
    }

    public void setCustomerDao(CustomerDataAccessObject customerDao) {

        this.customerDao = customerDao;
    }

    public AddressDataAccessObject getAddressDao() {

        return addressDao;
    }

    public void setAddressDao(AddressDataAccessObject addressDao) {

        this.addressDao = addressDao;
    }

    public void persistCustomer(Customer customer) {

        final Address address = customer.getAddress();

        final var optionalUniqueAddress = addressDao.findUniqueAddress(
                address.getCountryCode(),
                address.getPostalCode(),
                address.getCity(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getSuffix()
        );

        optionalUniqueAddress.ifPresentOrElse(customer::setAddress, () -> addressDao.persistAddress(address));

        customerDao.persistCustomer(customer);
    }

    public List<Customer> getArchivedCustomers() {

        return customerDao.getArchivedCustomers();
    }

    public List<Customer> getActiveCustomers() {

        return customerDao.getActiveCustomers();
    }

    public Optional<Customer> findCustomer(Integer id) {

        return customerDao.findByID(id);
    }

    public List<Customer> getAllCustomers() {

        return customerDao.getAll();
    }
}
