package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.cache.RouteRecreationQueue;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

public class CustomerOverviewController extends FXController {

    @FXML
    private Button viewCustomer;
    @FXML
    private Button editCustomer;
    @FXML
    private Button newCustomer;
    @FXML
    private TextField filter;

    @FXML
    private TableView<Customer> customers;
    @FXML
    private TableColumn<Customer, String> customerIdNo;
    @FXML
    private TableColumn<Customer, String> customerName;

    private final CustomerListService customerListService;

    private final Transition<Customer, ?> viewCustomerTransition;
    private final Transition<Customer, ?> editCustomerTransition;
    private final Transition<Void, ?> newCustomerTransition;
    private final CustomerOverviewModel viewModel;


    public CustomerOverviewController(CustomerListService customerListService,
                                      Transition<Customer, ?> viewCustomerTransition,
                                      Transition<Customer, ?> editCustomerTransition,
                                      Transition<Void, ?> newCustomerTransition,
                                      CustomerOverviewModel viewModel) {

        super("customer-overview.fxml");

        this.customerListService = customerListService;
        this.viewCustomerTransition = viewCustomerTransition;
        this.editCustomerTransition = editCustomerTransition;
        this.newCustomerTransition = newCustomerTransition;
        this.viewModel = viewModel;

        this.customerListService.setOnSucceeded(this::onCustomerListServiceSucceeded);
        this.customerListService.setOnFailed(this::onCustomerListServiceFailed);
    }

    @Override
    protected void initialize() {

        final var noCustomerSelected = customers.getSelectionModel().selectedItemProperty().isNull();

        viewCustomer.disableProperty().bind(noCustomerSelected);
        editCustomer.disableProperty().bind(noCustomerSelected);

        filter.textProperty().bindBidirectional(viewModel.customerFilterProperty());
        customerListService.nameOrCompanyNameProperty().bind(viewModel.customerFilterProperty());

        customerIdNo.setCellValueFactory(new PropertyValueFactory<>("idNo"));
        customerName.setCellValueFactory(cell -> Bindings.createStringBinding(
                () -> {
                    final Customer customer = cell.getValue();

                    return (!customer.getCompanyName().isEmpty())
                            ? customer.getCompanyName()
                            : customer.getLastName() + ", " + customer.getFirstName();
                }
        ));

        filter.textProperty().addListener(this::onFilterTextChanged);

        viewCustomer.setOnAction(this::onViewCustomerClicked);
        editCustomer.setOnAction(this::onEditCustomerClicked);
        newCustomer.setOnAction(this::onNewCustomerClicked);

        customers.getSelectionModel().selectedItemProperty().addListener(this::onCustomerSelectionChanged);
        customers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCustomersClicked);

        customerListService.start();
    }

    private void onCustomerSelectionChanged(Observable observable,
                                            Customer oldValue,
                                            Customer newValue) {

        final var selectedCustomerId = (newValue != null) ? newValue.getId() : 0;
        viewModel.setSelectedCustomerId(selectedCustomerId);
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        viewCustomer.setText(bundle.getString("view.customer_overview.view_customer"));
        editCustomer.setText(bundle.getString("view.customer_overview.edit_customer"));
        newCustomer.setText(bundle.getString("view.customer_overview.new_customer"));
        filter.setPromptText(bundle.getString("view.customer_overview.filter"));
        customerIdNo.setText(bundle.getString("view.customer_overview.table.idno"));
        customerName.setText(bundle.getString("view.customer_overview.table.name"));
    }

    private void onCustomerListServiceSucceeded(WorkerStateEvent event) {

        final var value = customerListService.getValue();
        final var selectedCustomer = value.stream()
                .filter(customer -> customer.getId() == viewModel.getSelectedCustomerId())
                .findFirst();

        customers.getItems().setAll(value);

        selectedCustomer.ifPresent(customers.getSelectionModel()::select);

        RouteRecreationQueue.getInstance().next().ifPresent(route -> {
            final Customer customer = customers.getSelectionModel().getSelectedItem();

            final String routeName = route.getRouteName();
            System.out.println(routeName);

            switch (routeName) {
                case "customer.editor" -> editCustomerTransition.orchestrate(customer);
                case "customer.dashboard" -> viewCustomerTransition.orchestrate(customer);
            }
        });
    }

    private void onCustomerListServiceFailed(WorkerStateEvent event) {

        throw new RuntimeException(customerListService.getException());
    }

    private void onViewCustomerClicked(ActionEvent event) {

        viewCustomerTransition.orchestrate(customers.getSelectionModel().getSelectedItem());
    }

    private void onEditCustomerClicked(ActionEvent event) {

        editCustomerTransition.orchestrate(customers.getSelectionModel().getSelectedItem());
    }

    private void onNewCustomerClicked(ActionEvent event) {

        newCustomerTransition.orchestrate(null);
    }

    private void onCustomersClicked(MouseEvent event) {

        if (event.getClickCount() != 2) return;

        final Customer customer = customers.getSelectionModel().getSelectedItem();

        if (customer == null) return;

        viewCustomerTransition.orchestrate(customer);
    }

    private void onFilterTextChanged(Observable observable,
                                     String oldValue,
                                     String newValue) {

        customerListService.restart();
    }
}
