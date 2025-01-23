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
import java.util.ResourceBundle;

public class CustomerDashboardController extends FXController {

    @FXML
    private Label idLabel;
    @FXML
    private Label id;
    @FXML
    private Label idNoLabel;
    @FXML
    private Label idNo;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label lastName;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label firstName;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label companyName;
    @FXML
    private Label emailAddressLabel;
    @FXML
    private Label emailAddress;
    @FXML
    private Label landlinePhoneNumberLabel;
    @FXML
    private Label landlinePhoneNumber;
    @FXML
    private Label mobilePhoneNumberLabel;
    @FXML
    private Label mobilePhoneNumber;
    @FXML
    private Label notesLabel;
    @FXML
    private Label notes;

    @FXML
    private Label countryCodeLabel;
    @FXML
    private Label countryCode;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label postalCode;
    @FXML
    private Label cityLabel;
    @FXML
    private Label city;
    @FXML
    private Label streetLabel;
    @FXML
    private Label street;
    @FXML
    private Label streetNumberLabel;
    @FXML
    private Label streetNumber;
    @FXML
    private Label suffixLabel;
    @FXML
    private Label suffix;

    @FXML
    private Button back;
    @FXML
    private Button editCustomer;
    @FXML
    private Button newTransaction;

    @FXML
    private Label transactionsLabel;
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
    protected void initialize() {

        idLabel.setLabelFor(id);
        idNoLabel.setLabelFor(idNo);
        lastNameLabel.setLabelFor(lastName);
        firstNameLabel.setLabelFor(firstName);
        companyNameLabel.setLabelFor(companyName);
        emailAddressLabel.setLabelFor(emailAddress);
        landlinePhoneNumberLabel.setLabelFor(landlinePhoneNumber);
        mobilePhoneNumberLabel.setLabelFor(mobilePhoneNumber);
        notesLabel.setLabelFor(notes);

        countryCodeLabel.setLabelFor(countryCode);
        postalCodeLabel.setLabelFor(postalCode);
        cityLabel.setLabelFor(city);
        streetLabel.setLabelFor(street);
        streetNumberLabel.setLabelFor(streetNumber);
        suffixLabel.setLabelFor(suffix);

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

    @Override
    protected void translate(ResourceBundle bundle) {

        idLabel.setText(bundle.getString("label.customer.dashboard.id"));
        idNoLabel.setText(bundle.getString("label.customer.dashboard.idno"));
        lastNameLabel.setText(bundle.getString("label.customer.dashboard.last.name"));
        firstNameLabel.setText(bundle.getString("label.customer.dashboard.first.name"));
        companyNameLabel.setText(bundle.getString("label.customer.dashboard.company.name"));
        emailAddressLabel.setText(bundle.getString("label.customer.dashboard.email.address"));
        landlinePhoneNumberLabel.setText(bundle.getString("label.customer.dashboard.landline.phone.number"));
        mobilePhoneNumberLabel.setText(bundle.getString("label.customer.dashboard.mobile.phone.number"));
        notesLabel.setText(bundle.getString("label.customer.dashboard.notes"));
        countryCodeLabel.setText(bundle.getString("label.customer.dashboard.country.code"));
        postalCodeLabel.setText(bundle.getString("label.customer.dashboard.postal.code"));
        cityLabel.setText(bundle.getString("label.customer.dashboard.city"));
        streetLabel.setText(bundle.getString("label.customer.dashboard.street"));
        streetNumberLabel.setText(bundle.getString("label.customer.dashboard.street.number"));
        suffixLabel.setText(bundle.getString("label.customer.dashboard.suffix"));

        back.setText(bundle.getString("button.customer.dashboard.back"));
        editCustomer.setText(bundle.getString("button.customer.dashboard.edit.customer"));
        newTransaction.setText(bundle.getString("button.customer.dashboard.new.transaction"));

        transactionsLabel.setText(bundle.getString("label.customer.dashboard.transactions"));
        transactionId.setText(bundle.getString("table.customer.dashboard.id"));
        transactionDescription.setText(bundle.getString("label.customer.dashboard.table.description"));
        transactionCreated.setText(bundle.getString("label.customer.dashboard.table.created"));
        transactionLastModified.setText(bundle.getString("table.customer.dashboard.last.modified"));
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
