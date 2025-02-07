package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerOverview extends FXView<CustomerOverviewModel> {

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

    public CustomerOverview(CustomerOverviewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "customer-overview.fxml";
    }

    @Override
    public void initialize() {

        customerIdNo.setCellValueFactory(new PropertyValueFactory<>("idNo"));
        customerName.setCellValueFactory(cell -> {
            final var customer = cell.getValue();
            final var companyName = customer.getCompanyName();

            final var name = (companyName.isEmpty())
                    ? customer.getFullName()
                    : companyName;

            return new ReadOnlyStringWrapper(name);
        });

        viewCustomer.disableProperty().bind(viewModel().viewDisabledProperty());
        editCustomer.disableProperty().bind(viewModel().editDisabledProperty());
        filter.textProperty().bindBidirectional(viewModel().filterProperty());
        customers.itemsProperty().bind(viewModel().customersProperty());
        viewModel().selectedCustomerProperty().bind(customers.getSelectionModel().selectedItemProperty());

        viewCustomer.setOnAction(_ -> viewModel().onViewCustomerClicked());
        editCustomer.setOnAction(_ -> viewModel().onEditCustomerClicked());
        newCustomer.setOnAction(_ -> viewModel().onNewCustomerClicked());
        customers.setOnMouseClicked(event -> viewModel().onCustomersClicked(event.getClickCount()));
    }
}
