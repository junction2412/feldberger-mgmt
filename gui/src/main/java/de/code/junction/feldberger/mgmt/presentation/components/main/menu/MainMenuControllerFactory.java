package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard.CustomerDashboardController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.AddressViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerEditorController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerListService;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerOverviewModel;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

public class MainMenuControllerFactory {

    private final PersistenceManager persistenceManager;

    public MainMenuControllerFactory(PersistenceManager persistenceManager) {

        this.persistenceManager = persistenceManager;
    }

    public FXController customerOverview(HashMap<String, Object> cache,
                                         Runnable onNewCustomerClicked,
                                         Consumer<Integer> onEditCustomerClicked,
                                         Consumer<Integer> onViewCustomerClicked) {

        final var selectedCustomerId = (int) cache.getOrDefault("selectedCustomerId", 0);
        final var filter = (String) cache.getOrDefault("filter", "");

        final var viewModel = new CustomerOverviewModel(selectedCustomerId, filter);

        viewModel.selectedCustomerIdProperty().addListener((_, _, value) ->
                cache.put("selectedCustomerId", value));

        viewModel.customerFilterProperty().addListener((_, _, value) ->
                cache.put("filter", value));

        final var customerListService = new CustomerListService(persistenceManager.customerDao());

        return new CustomerOverviewController(
                customerListService,
                viewModel,
                onNewCustomerClicked,
                onEditCustomerClicked,
                onViewCustomerClicked
        );
    }

    public FXController customerEditor(Runnable onBackClicked,
                                       Consumer<Customer> onSaveClicked,
                                       HashMap<String, Object> cache) {

        final var customerId = (int) cache.get("customerId");
        final var customer = persistenceManager.customerDao()
                .findById(customerId)
                .orElseGet(Customer::new);

        final var address = Optional.ofNullable(customer.getAddress())
                .orElseGet(Address::new);

        final var addressViewModel = new AddressViewModel(
                address.getId(),
                (String) cache.getOrDefault("countryCode", address.getCountryCode()),
                (String) cache.getOrDefault("postalCode", address.getPostalCode()),
                (String) cache.getOrDefault("city", address.getCity()),
                (String) cache.getOrDefault("street", address.getStreet()),
                (String) cache.getOrDefault("streetNumber", address.getStreetNumber()),
                (String) cache.getOrDefault("suffix", address.getSuffix())
        );

        addressViewModel.countryCodeProperty().addListener((_, _, value) ->
                cache.put("countryCode", value));
        addressViewModel.postalCodeProperty().addListener((_, _, value) ->
                cache.put("postalCode", value));
        addressViewModel.cityProperty().addListener((_, _, value) ->
                cache.put("city", value));
        addressViewModel.streetProperty().addListener((_, _, value) ->
                cache.put("street", value));
        addressViewModel.streetNumberProperty().addListener((_, _, value) ->
                cache.put("streetNumber", value));
        addressViewModel.suffixProperty().addListener((_, _, value) ->
                cache.put("suffix", value));

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
                addressViewModel
        );

        viewModel.idNoProperty().addListener((_, _, value) ->
                cache.put("idNo", value));
        viewModel.lastNameProperty().addListener((_, _, value) ->
                cache.put("lastName", value));
        viewModel.firstNameProperty().addListener((_, _, value) ->
                cache.put("firstName", value));
        viewModel.companyNameProperty().addListener((_, _, value) ->
                cache.put("companyName", value));
        viewModel.emailAddressProperty().addListener((_, _, value) ->
                cache.put("emailAddress", value));
        viewModel.landlinePhoneNumberProperty().addListener((_, _, value) ->
                cache.put("landlinePhoneNumber", value));
        viewModel.mobilePhoneNumberProperty().addListener((_, _, value) ->
                cache.put("mobilePhoneNumber", value));
        viewModel.notesProperty().addListener((_, _, value) ->
                cache.put("notes", value));

        return new CustomerEditorController(viewModel, onBackClicked, onSaveClicked);
    }

    public FXController customerDashboard(Runnable onBackClicked,
                                          Consumer<Integer> onEditCustomerClicked,
                                          Consumer<Integer> onNewTransactionClicked,
                                          HashMap<String, Object> cache) {

        final var customerId = (int) cache.get("customerId");
        final var customer = persistenceManager.customerDao()
                .findById(customerId)
                .orElseThrow();

        return new CustomerDashboardController(
                new CustomerViewModel(customer),
                onBackClicked,
                onEditCustomerClicked,
                onNewTransactionClicked
        );
    }
}
