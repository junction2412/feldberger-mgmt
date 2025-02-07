package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.WorkerStateEvent;

public class CustomerOverviewModel extends FXViewModel {

    private final BooleanProperty viewDisabled;
    private final BooleanProperty editDisabled;

    private final StringProperty filter;
    private final ListProperty<Customer> customers;
    private final ObjectProperty<Customer> selectedCustomer;

    private final Navigator<MainMenuRoute> navigator;
    private final CustomerListService customerListService;

    public CustomerOverviewModel(Messenger messenger,
                                 Navigator<MainMenuRoute> navigator,
                                 CustomerListService customerListService) {

        super(messenger);

        this.navigator = navigator;
        this.customerListService = customerListService;

        this.viewDisabled = new SimpleBooleanProperty(false);
        this.editDisabled = new SimpleBooleanProperty(false);

        this.filter = new SimpleStringProperty("");
        this.customers = new SimpleListProperty<>(FXCollections.emptyObservableList());
        this.selectedCustomer = new SimpleObjectProperty<>();
    }

    @Override
    public void init() {

        viewDisabled.bind(selectedCustomer.isNull());
        editDisabled.bind(selectedCustomer.isNull());

        customerListService.setOnSucceeded(this::onCustomerListServiceSucceeded);
        customerListService.start();
    }

    public void onViewCustomerClicked() {

        final var selectedCustomer = getSelectedCustomer();
        if (selectedCustomer == null)
            return;

        navigator.navigateTo(new MainMenuRoute.CustomerDashboard(selectedCustomer.getId()));
    }

    public void onEditCustomerClicked() {

        final var selectedCustomer = getSelectedCustomer();
        if (selectedCustomer == null)
            return;

        navigator.navigateTo(new MainMenuRoute.CustomerEditor(selectedCustomer.getId(), false));
    }

    public void onNewCustomerClicked() {
        navigator.navigateTo(new MainMenuRoute.CustomerEditor());
    }

    public void onCustomersClicked(int count) {

        if (count != 2)
            return;

        onViewCustomerClicked();
    }

    private void onCustomerListServiceSucceeded(WorkerStateEvent event) {

        final var customerList = customerListService.getValue();
        final var filteredList = new FilteredList<>(customerList);

        filteredList.predicateProperty().bind(Bindings.createObjectBinding(
                () -> customer -> {
                    final var text = filter.get().toLowerCase();
                    final var lastName = customer.getLastName().toLowerCase();
                    final var firstName = customer.getFirstName().toLowerCase();
                    final var companyName = customer.getCompanyName().toLowerCase();

                    return text.isBlank() || lastName.contains(text) || firstName.contains(text)
                            || companyName.contains(text);
                },
                filter
        ));

        customers.set(filteredList);
    }

    public boolean isViewDisabled() {
        return viewDisabled.get();
    }

    public BooleanProperty viewDisabledProperty() {
        return viewDisabled;
    }

    public boolean isEditDisabled() {
        return editDisabled.get();
    }

    public BooleanProperty editDisabledProperty() {
        return editDisabled;
    }

    public String getFilter() {
        return filter.get();
    }

    public StringProperty filterProperty() {
        return filter;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer.get();
    }

    public ObjectProperty<Customer> selectedCustomerProperty() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer.set(selectedCustomer);
    }

    public ObservableList<Customer> getCustomers() {
        return customers.get();
    }

    public ListProperty<Customer> customersProperty() {
        return customers;
    }
}
