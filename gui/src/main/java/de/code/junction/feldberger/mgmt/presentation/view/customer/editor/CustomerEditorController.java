package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CustomerEditorController extends FXController {

    @FXML
    private Label idLabel;
    @FXML
    private Label id;
    @FXML
    private Label idNoLabel;
    @FXML
    private TextField idNo;
    @FXML
    private Label lastNameLabel;
    @FXML
    private TextField lastName;
    @FXML
    private Label firstNameLabel;
    @FXML
    private TextField firstName;
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField companyName;
    @FXML
    private Label emailAddressLabel;
    @FXML
    private TextField emailAddress;
    @FXML
    private Label landlinePhoneNumberLabel;
    @FXML
    private TextField landlinePhoneNumber;
    @FXML
    private Label mobilePhoneNumberLabel;
    @FXML
    private TextField mobilePhoneNumber;
    @FXML
    private Label notesLabel;
    @FXML
    private TextArea notes;

    @FXML
    private Label countryCodeLabel;
    @FXML
    private TextField countryCode;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private TextField postalCode;
    @FXML
    private Label cityLabel;
    @FXML
    private TextField city;
    @FXML
    private Label streetLabel;
    @FXML
    private TextField street;
    @FXML
    private Label streetNumberLabel;
    @FXML
    private TextField streetNumber;
    @FXML
    private Label suffixLabel;
    @FXML
    private TextField suffix;

    @FXML
    private Button back;
    @FXML
    private Button save;

    private final CustomerViewModel viewModel;
    private final Runnable onBackClicked;
    private final Consumer<Customer> onSaveClicked;

    public CustomerEditorController(CustomerViewModel viewModel,
                                    Runnable onBackClicked,
                                    Consumer<Customer> onSaveClicked) {

        super("customer-editor-view.fxml");

        this.viewModel = viewModel;
        this.onSaveClicked = onSaveClicked;
        this.onBackClicked = onBackClicked;
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
        save.setOnAction(this::onSaveClicked);
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        idLabel.setText(bundle.getString("label.customer.editor.id"));
        idNoLabel.setText(bundle.getString("label.customer.editor.idno"));
        lastNameLabel.setText(bundle.getString("label.customer.editor.last.name"));
        firstNameLabel.setText(bundle.getString("label.customer.editor.first.name"));
        companyNameLabel.setText(bundle.getString("label.customer.editor.company.name"));
        emailAddressLabel.setText(bundle.getString("label.customer.editor.email.address"));
        landlinePhoneNumberLabel.setText(bundle.getString("label.customer.editor.landline.phone.number"));
        mobilePhoneNumberLabel.setText(bundle.getString("label.customer.editor.mobile.phone.number"));
        notesLabel.setText(bundle.getString("label.customer.editor.notes"));
        countryCodeLabel.setText(bundle.getString("label.customer.editor.country.code"));
        postalCodeLabel.setText(bundle.getString("label.customer.editor.postal.code"));
        cityLabel.setText(bundle.getString("label.customer.editor.city"));
        streetLabel.setText(bundle.getString("label.customer.editor.street"));
        streetNumberLabel.setText(bundle.getString("label.customer.editor.street.number"));
        suffixLabel.setText(bundle.getString("label.customer.editor.suffix"));

        back.setText(bundle.getString("button.customer.editor.back"));
        save.setText(bundle.getString("button.customer.editor.save"));
    }

    private void onBackClicked(ActionEvent event) {

        onBackClicked.run();
    }

    private void onSaveClicked(ActionEvent event) {

        onSaveClicked.accept(viewModel.toCustomer());
    }
}
