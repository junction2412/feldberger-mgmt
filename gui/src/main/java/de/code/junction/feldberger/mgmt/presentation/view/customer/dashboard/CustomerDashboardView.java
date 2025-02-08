package de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard;

import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.Objects;

public class CustomerDashboardView extends FXView<CustomerDashboardViewModel> {

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
    private TableView<Transaction> transactions;
    @FXML
    private TableColumn<Transaction, String> transactionId;
    @FXML
    private TableColumn<Transaction, String> transactionDescription;
    @FXML
    private TableColumn<Transaction, LocalDateTime> transactionCreated;
    @FXML
    private TableColumn<Transaction, LocalDateTime> transactionLastModified;

    public CustomerDashboardView(CustomerDashboardViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "customer-dashboard-view.fxml";
    }

    @Override
    public void initialize() {

        id.textProperty().bind(viewModel().idProperty().asString());
        idNo.textProperty().bindBidirectional(viewModel().idNoProperty());
        lastName.textProperty().bindBidirectional(viewModel().lastNameProperty());
        firstName.textProperty().bindBidirectional(viewModel().firstNameProperty());
        companyName.textProperty().bindBidirectional(viewModel().companyNameProperty());
        emailAddress.textProperty().bindBidirectional(viewModel().emailAddressProperty());
        landlinePhoneNumber.textProperty().bindBidirectional(viewModel().landlinePhoneNumberProperty());
        mobilePhoneNumber.textProperty().bindBidirectional(viewModel().mobilePhoneNumberProperty());
        notes.textProperty().bindBidirectional(viewModel().notesProperty());

        countryCode.textProperty().bindBidirectional(viewModel().countryCodeProperty());
        postalCode.textProperty().bindBidirectional(viewModel().postalCodeProperty());
        city.textProperty().bindBidirectional(viewModel().cityProperty());
        street.textProperty().bindBidirectional(viewModel().streetProperty());
        streetNumber.textProperty().bindBidirectional(viewModel().streetNumberProperty());
        suffix.textProperty().bindBidirectional(viewModel().suffixProperty());

        transactions.itemsProperty().bind(viewModel().transactionsProperty());

        transactionId.setCellValueFactory(new PropertyValueFactory<>("idNo"));
        transactionDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        transactionCreated.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        transactionLastModified.setCellValueFactory(new PropertyValueFactory<>("modificationDate"));

        back.setOnAction(_ -> viewModel().onBackClicked());
        editCustomer.setOnAction(_ -> viewModel().onEditClicked());
        newTransaction.setOnAction(_ -> viewModel().onCreateTransactionClicked());
        transactions.setOnMouseClicked(event -> viewModel().onTransactionClicked(event.getClickCount()));

        final var selectionModel = transactions.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((_, _, newValue) -> {
            if (!Objects.equals(viewModel().getSelectedTransaction(), newValue))
                viewModel().setSelectedTransaction(newValue);
        });

        viewModel().selectedTransactionProperty().addListener((_, _, newValue) -> {
            if (!Objects.equals(selectionModel.getSelectedItem(), newValue))
                selectionModel.select(newValue);
        });
    }
}
