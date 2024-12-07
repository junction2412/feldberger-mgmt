package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.cache.RouteRecreationQueue;
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

        final var current = RouteRecreationQueue.getInstance().current();
        final var cache = (current != null)
                ? current.getCache()
                : Map.of();

        customerId = (cache.containsKey("selectedCustomerId"))
                ? (int) cache.get("selectedCustomerId")
                : customerId;

        final var filter = (cache.containsKey("filter"))
                ? (String) cache.get("filter")
                : "";

        final var customerOverviewModel = new CustomerOverviewModel(
                customerId,
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

        final var current = RouteRecreationQueue.getInstance().current();
        final var cache = (current != null)
                ? current.getCache()
                : Map.of();

        final var address = customer.getAddress();

        final var viewModel = new CustomerViewModel(
                customer.getId(),
                (cache.containsKey("idNo")) ? (String) cache.get("idNo") : customer.getIdNo(),
                (cache.containsKey("lastName")) ? (String) cache.get("lastName") : customer.getLastName(),
                (cache.containsKey("firstName")) ? (String) cache.get("firstName") : customer.getFirstName(),
                (cache.containsKey("companyName")) ? (String) cache.get("companyName") : customer.getCompanyName(),
                (cache.containsKey("emailAddress")) ? (String) cache.get("emailAddress") : customer.getEmailAddress(),
                (cache.containsKey("landlinePhoneNumber")) ? (String) cache.get("landlinePhoneNumber") : customer.getLandlinePhoneNumber(),
                (cache.containsKey("mobilePhoneNumber")) ? (String) cache.get("mobilePhoneNumber") : customer.getMobilePhoneNumber(),
                (cache.containsKey("notes")) ? (String) cache.get("notes") : customer.getNotes(),
                customer.isArchived(),
                customer.getArchiveDate(),
                address == null
                        ? new AddressViewModel()

                        : new AddressViewModel(
                        address.getId(),
                        (cache.containsKey("countryCode")) ? (String) cache.get("countryCode") : address.getCountryCode(),
                        (cache.containsKey("postalCode")) ? (String) cache.get("postalCode") : address.getPostalCode(),
                        (cache.containsKey("city")) ? (String) cache.get("city") : address.getCity(),
                        (cache.containsKey("street")) ? (String) cache.get("street") : address.getStreet(),
                        (cache.containsKey("streetNumber")) ? (String) cache.get("streetNumber") : address.getStreetNumber(),
                        (cache.containsKey("suffix")) ? (String) cache.get("suffix") : address.getSuffix()
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
