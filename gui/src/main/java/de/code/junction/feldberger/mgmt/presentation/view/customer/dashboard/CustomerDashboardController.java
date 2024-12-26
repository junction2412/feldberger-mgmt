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
import java.util.function.Consumer;

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
    private final Consumer<Integer> onEditCustomerClicked;
    private final Consumer<Integer> onNewTransactionClicked;

    public CustomerDashboardController(CustomerViewModel viewModel,
                                       Runnable onBackClicked,
                                       Consumer<Integer> onEditCustomerClicked,
                                       Consumer<Integer> onNewTransactionClicked) {

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

        idLabel.setText(bundle.getString("view.customer_dashboard.id"));
        idNoLabel.setText(bundle.getString("view.customer_dashboard.idno"));
        lastNameLabel.setText(bundle.getString("view.customer_dashboard.last_name"));
        firstNameLabel.setText(bundle.getString("view.customer_dashboard.first_name"));
        companyNameLabel.setText(bundle.getString("view.customer_dashboard.company_name"));
        emailAddressLabel.setText(bundle.getString("view.customer_dashboard.email_address"));
        landlinePhoneNumberLabel.setText(bundle.getString("view.customer_dashboard.landline_phone_number"));
        mobilePhoneNumberLabel.setText(bundle.getString("view.customer_dashboard.mobile_phone_number"));
        notesLabel.setText(bundle.getString("view.customer_dashboard.notes"));
        countryCodeLabel.setText(bundle.getString("view.customer_dashboard.country_code"));
        postalCodeLabel.setText(bundle.getString("view.customer_dashboard.postal_code"));
        cityLabel.setText(bundle.getString("view.customer_dashboard.city"));
        streetLabel.setText(bundle.getString("view.customer_dashboard.street"));
        streetNumberLabel.setText(bundle.getString("view.customer_dashboard.street_number"));
        suffixLabel.setText(bundle.getString("view.customer_dashboard.suffix"));

        back.setText(bundle.getString("view.customer_dashboard.back"));
        editCustomer.setText(bundle.getString("view.customer_dashboard.edit_customer"));
        newTransaction.setText(bundle.getString("view.customer_dashboard.new_transaction"));

        transactionsLabel.setText(bundle.getString("view.customer_dashboard.transactions"));
        transactionId.setText(bundle.getString("view.customer_dashboard.table.id"));
        transactionDescription.setText(bundle.getString("view.customer_dashboard.table.description"));
        transactionCreated.setText(bundle.getString("view.customer_dashboard.table.created"));
        transactionLastModified.setText(bundle.getString("view.customer_dashboard.table.last_modified"));
    }

    private void onBackClicked(ActionEvent event) {

        onBackClicked.run();
    }

    private void onEditCustomerClicked(ActionEvent event) {

        onEditCustomerClicked.accept(viewModel.getId());
    }

    private void onNewTransactionClicked(ActionEvent event) {

        onNewTransactionClicked.accept(viewModel.getId());
    }
}
