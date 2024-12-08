package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.AddressViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerListService;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;

import java.util.Map;

public class MainMenuControllerFactory {

    private final PersistenceManager persistenceManager;

    public MainMenuControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }

    public FXController customerOverview(Transition<Customer, ?> viewCustomerTransition,
                                         Transition<Customer, ?> editCustomerTransition,
                                         Transition<Void, ?> newCustomerTransition,
                                         int customerId) {

        final var cache = Map.of();

        final var filter = (String) cache.getOrDefault("filter", "");

        final var customerOverviewModel = new CustomerOverviewModel(
                (int) cache.getOrDefault("selectedCustomerId", customerId),
                filter
        );

        final var customerListService = new CustomerListService(persistenceManager.customerDao());

        return new CustomerOverviewController(
                customerListService,
                viewCustomerTransition,
                editCustomerTransition,
                newCustomerTransition,
                customerOverviewModel
        );
    }

    public FXController customerEditor(Customer customer,
                                       Transition<Customer, ?> backTransition,
                                       Transition<Customer, ?> saveTransition) {

        final var cache = Map.of();

        final var address = customer.getAddress();

        final var viewModel = new CustomerViewModel(
                customer.getId(),
                (String) cache.getOrDefault("idNo", customer.getIdNo()),
                (String) cache.getOrDefault("lastName", customer.getLastName()),
                (String) cache.getOrDefault("firstName", customer.getFirstName()),
                (String) cache.getOrDefault("companyName", customer.getCompanyName()),
                (String) cache.getOrDefault("emailAddress", customer.getEmailAddress()),
                (String) cache.getOrDefault("landlinePhoneNumber", customer.getLandlinePhoneNumber()),
                (String) cache.getOrDefault("mobilePhoneNumber", customer.getMobilePhoneNumber()),
                (String) cache.getOrDefault("notes", customer.getNotes()),
                customer.isArchived(),
                customer.getArchiveDate(),
                address == null
                        ? new AddressViewModel()
                        : new AddressViewModel(
                        address.getId(),
                        (String) cache.getOrDefault("countryCode", address.getCountryCode()),
                        (String) cache.getOrDefault("postalCode", address.getPostalCode()),
                        (String) cache.getOrDefault("city", address.getCity()),
                        (String) cache.getOrDefault("street", address.getStreet()),
                        (String) cache.getOrDefault("streetNumber", address.getStreetNumber()),
                        (String) cache.getOrDefault("suffix", address.getSuffix())
                )
        );

        return new CustomerEditorController(
                viewModel,
                backTransition,
                saveTransition
        );
    }

    public FXController customerDashboard(Customer customer,
                                          Transition<Customer, ?> backTransition,
                                          Transition<Customer, ?> editCustomerTransition,
                                          Transition<Customer, ?> newTransactionTransition) {

        return new CustomerDashboardController(
                new CustomerViewModel(customer),
                backTransition,
                editCustomerTransition,
                newTransactionTransition
        );
    }
}
