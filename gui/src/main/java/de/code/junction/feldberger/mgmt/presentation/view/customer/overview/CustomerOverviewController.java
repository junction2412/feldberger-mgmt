package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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


    public CustomerOverviewController(CustomerListService customerListService,
                                      Transition<Customer, ?> viewCustomerTransition,
                                      Transition<Customer, ?> editCustomerTransition,
                                      Transition<Void, ?> newCustomerTransition) {

        super("customer-overview.fxml");

        this.customerListService = customerListService;
        this.viewCustomerTransition = viewCustomerTransition;
        this.editCustomerTransition = editCustomerTransition;
        this.newCustomerTransition = newCustomerTransition;
    }

    @Override
    protected void initialize() {

        final var noCustomerSelected = customers.getSelectionModel().selectedItemProperty().isNull();

        viewCustomer.disableProperty().bind(noCustomerSelected);
        editCustomer.disableProperty().bind(noCustomerSelected);

        customerListService.nameOrCompanyNameProperty().bind(filter.textProperty());
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

        customerListService.start();
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

    private void onViewCustomerClicked(ActionEvent event) {

        viewCustomerTransition.orchestrate(customers.getSelectionModel().getSelectedItem());
    }

    private void onEditCustomerClicked(ActionEvent event) {

        editCustomerTransition.orchestrate(customers.getSelectionModel().getSelectedItem());
    }

    private void onNewCustomerClicked(ActionEvent event) {

        newCustomerTransition.orchestrate(null);
    }

    private void onFilterTextChanged(Observable observable, String oldValue, String newValue) {

        customerListService.restart();
    }
}
