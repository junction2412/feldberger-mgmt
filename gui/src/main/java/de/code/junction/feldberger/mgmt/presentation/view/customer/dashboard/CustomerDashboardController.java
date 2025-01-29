package de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard;

import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import de.code.junction.feldberger.mgmt.presentation.view.customer.editor.CustomerViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;

public class CustomerDashboardController extends FXController {

    @FXML
    private Label id;
    @FXML
    private Label idNo;
    @FXML
    private Label lastName;
    @FXML
    private Label firstName;
    @FXML
    private Label companyName;
    @FXML
    private Label emailAddress;
    @FXML
    private Label landlinePhoneNumber;
    @FXML
    private Label mobilePhoneNumber;
    @FXML
    private Label notes;

    @FXML
    private Label countryCode;
    @FXML
    private Label postalCode;
    @FXML
    private Label city;
    @FXML
    private Label street;
    @FXML
    private Label streetNumber;
    @FXML
    private Label suffix;

    @FXML
    private Button back;
    @FXML
    private Button editCustomer;
    @FXML
    private Button newTransaction;

    @FXML
    private TableView<Object> transactions;
    @FXML
    private TableColumn<Object, Integer> transactionId;
    @FXML
    private TableColumn<Object, String> transactionDescription;
    @FXML
    private TableColumn<Object, LocalDateTime> transactionCreated;
    @FXML
    private TableColumn<Object, LocalDateTime> transactionLastModified;

    private final CustomerViewModel viewModel;

    private final Runnable onBackClicked;
    private final Runnable onEditCustomerClicked;
    private final Runnable onNewTransactionClicked;

    public CustomerDashboardController(CustomerViewModel viewModel,
                                       Runnable onBackClicked,
                                       Runnable onEditCustomerClicked,
                                       Runnable onNewTransactionClicked) {

        super("customer-dashboard-view.fxml");

        this.viewModel = viewModel;

        this.onBackClicked = onBackClicked;
        this.onEditCustomerClicked = onEditCustomerClicked;
        this.onNewTransactionClicked = onNewTransactionClicked;
    }

    @Override
    public void initialize() {

        id.textProperty().bind(viewModel.idProperty().asString());
        idNo.textProperty().bindBidirectional(viewModel.idNoProperty());
        lastName.textProperty().bindBidirectional(viewModel.lastNameProperty());
        firstName.textProperty().bindBidirectional(viewModel.firstNameProperty());
        companyName.textProperty().bindBidirectional(viewModel.companyNameProperty());
        emailAddress.textProperty().bindBidirectional(viewModel.emailAddressProperty());
        landlinePhoneNumber.textProperty().bindBidirectional(viewModel.landlinePhoneNumberProperty());
        mobilePhoneNumber.textProperty().bindBidirectional(viewModel.mobilePhoneNumberProperty());
        notes.textProperty().bindBidirectional(viewModel.notesProperty());

        final var addressViewModel = viewModel.getAddressViewModel();

        countryCode.textProperty().bindBidirectional(addressViewModel.countryCodeProperty());
        postalCode.textProperty().bindBidirectional(addressViewModel.postalCodeProperty());
        city.textProperty().bindBidirectional(addressViewModel.cityProperty());
        street.textProperty().bindBidirectional(addressViewModel.streetProperty());
        streetNumber.textProperty().bindBidirectional(addressViewModel.streetNumberProperty());
        suffix.textProperty().bindBidirectional(addressViewModel.suffixProperty());

        back.setOnAction(this::onBackClicked);
        editCustomer.setOnAction(this::onEditCustomerClicked);
        newTransaction.setOnAction(this::onNewTransactionClicked);
    }

    private void onBackClicked(ActionEvent event) {

        onBackClicked.run();
    }

    private void onEditCustomerClicked(ActionEvent event) {

        onEditCustomerClicked.run();
    }

    private void onNewTransactionClicked(ActionEvent event) {

        onNewTransactionClicked.run();
    }
}
