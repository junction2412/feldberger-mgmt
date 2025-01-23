package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;
import java.util.function.Consumer;

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
    private TableView<CustomerItemViewModel> customers;
    @FXML
    private TableColumn<CustomerItemViewModel, String> customerIdNo;
    @FXML
    private TableColumn<CustomerItemViewModel, String> customerName;

    private final CustomerListService customerListService;

    private final Consumer<Integer> onViewCustomerClicked;
    private final Consumer<Integer> onEditCustomerClicked;
    private final Runnable onNewCustomerClicked;

    private final CustomerOverviewModel viewModel;

    public CustomerOverviewController(CustomerListService customerListService,
                                      CustomerOverviewModel viewModel,
                                      Runnable onNewCustomerClicked,
                                      Consumer<Integer> onEditCustomerClicked,
                                      Consumer<Integer> onViewCustomerClicked) {

        super("customer-overview.fxml");

        this.customerListService = customerListService;
        this.viewModel = viewModel;

        this.onNewCustomerClicked = onNewCustomerClicked;
        this.onViewCustomerClicked = onViewCustomerClicked;
        this.onEditCustomerClicked = onEditCustomerClicked;

        this.customerListService.setOnSucceeded(this::onCustomerListServiceSucceeded);
        this.customerListService.setOnFailed(this::onCustomerListServiceFailed);

    }

    @Override
    protected void initialize() {

        final var noCustomerSelected = customers.getSelectionModel().selectedItemProperty().isNull();

        viewCustomer.disableProperty().bind(noCustomerSelected);
        editCustomer.disableProperty().bind(noCustomerSelected);

        filter.textProperty().bindBidirectional(viewModel.customerFilterProperty());

        customerIdNo.setCellValueFactory(cell -> cell.getValue().idNoProperty());
        customerName.setCellValueFactory(cell -> cell.getValue().nameOrCompanyNameProperty());

        viewCustomer.setOnAction(this::onViewCustomerClicked);
        editCustomer.setOnAction(this::onEditCustomerClicked);
        newCustomer.setOnAction(this::onNewCustomerClicked);

        customers.getSelectionModel().selectedItemProperty().addListener(this::onCustomerSelectionChanged);
        customers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCustomersClicked);

        customerListService.start();
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        viewCustomer.setText(bundle.getString("button.customer.overview.view.customer"));
        editCustomer.setText(bundle.getString("button.customer.overview.edit.customer"));
        newCustomer.setText(bundle.getString("button.customer.overview.new.customer"));
        filter.setPromptText(bundle.getString("prompt.customer.overview.filter"));
        customerIdNo.setText(bundle.getString("table.column.customer.overview.idno"));
        customerName.setText(bundle.getString("table.column.customer.overview.name"));
    }

    private void onCustomerSelectionChanged(Observable observable,
                                            CustomerItemViewModel oldValue,
                                            CustomerItemViewModel newValue) {

        final var selectedCustomerId = (newValue != null) ? newValue.getId() : 0;

        viewModel.setSelectedCustomerId(selectedCustomerId);
    }

    private void onCustomerListServiceSucceeded(WorkerStateEvent event) {

        final var loadedCustomers = customerListService.getValue();
        final var selectedCustomer = loadedCustomers.stream()
                .filter(customer -> customer.getId() == viewModel.getSelectedCustomerId())
                .findFirst();

        final var filteredList = new FilteredList<>(loadedCustomers);
        filteredList.predicateProperty().bind(Bindings.createObjectBinding(
                () -> customer -> {
                    final var text = filter.getText().toLowerCase();
                    final var name = customer.getNameOrCompanyName().toLowerCase();

                    return name.contains(text);
                },
                filter.textProperty()
        ));

        customers.setItems(filteredList);

        selectedCustomer.ifPresent(customers.getSelectionModel()::select);
    }

    private void onCustomerListServiceFailed(WorkerStateEvent event) {

        throw new RuntimeException(customerListService.getException());
    }

    private void onViewCustomerClicked(ActionEvent event) {

        final var customer = customers.getSelectionModel().getSelectedItem();

        if (customer == null) return;

        onViewCustomerClicked.accept(customer.getId());
    }

    private void onEditCustomerClicked(ActionEvent event) {

        final var customer = customers.getSelectionModel().getSelectedItem();

        if (customer == null) return;

        onEditCustomerClicked.accept(customer.getId());
    }

    private void onNewCustomerClicked(ActionEvent event) {

        onNewCustomerClicked.run();
    }

    private void onCustomersClicked(MouseEvent event) {

        if (event.getClickCount() != 2) return;

        final var customer = customers.getSelectionModel().getSelectedItem();

        if (customer == null) return;

        onViewCustomerClicked.accept(customer.getId());
    }
}
