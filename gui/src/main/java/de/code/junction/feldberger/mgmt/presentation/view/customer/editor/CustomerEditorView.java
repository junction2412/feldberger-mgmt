package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CustomerEditorView extends FXView<CustomerEditorViewModel> {

    @FXML
    private Label id;
    @FXML
    private TextField idNo;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField companyName;
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField landlinePhoneNumber;
    @FXML
    private TextField mobilePhoneNumber;
    @FXML
    private TextArea notes;

    @FXML
    private TextField countryCode;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField city;
    @FXML
    private TextField street;
    @FXML
    private TextField streetNumber;
    @FXML
    private TextField suffix;

    @FXML
    private Button back;
    @FXML
    private Button save;

    public CustomerEditorView(CustomerEditorViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "customer-editor-view.fxml";
    }

    @Override
    public void initialize() {

        save.disableProperty().bind(viewModel().saveDisabledProperty());

        id.textProperty().bind(viewModel().idProperty().asString());
        idNo.textProperty().bindBidirectional(viewModel().newIdNoProperty());
        lastName.textProperty().bindBidirectional(viewModel().newLastNameProperty());
        firstName.textProperty().bindBidirectional(viewModel().newFirstNameProperty());
        companyName.textProperty().bindBidirectional(viewModel().newCompanyNameProperty());
        emailAddress.textProperty().bindBidirectional(viewModel().newEmailAddressProperty());
        landlinePhoneNumber.textProperty().bindBidirectional(viewModel().newLandlinePhoneNumberProperty());
        mobilePhoneNumber.textProperty().bindBidirectional(viewModel().newMobilePhoneNumberProperty());
        notes.textProperty().bindBidirectional(viewModel().newNotesProperty());

        countryCode.textProperty().bindBidirectional(viewModel().newCountryCodeProperty());
        postalCode.textProperty().bindBidirectional(viewModel().newPostalCodeProperty());
        city.textProperty().bindBidirectional(viewModel().newCityProperty());
        street.textProperty().bindBidirectional(viewModel().newStreetProperty());
        streetNumber.textProperty().bindBidirectional(viewModel().newStreetNumberProperty());
        suffix.textProperty().bindBidirectional(viewModel().newSuffixProperty());

        back.setOnAction(_ -> viewModel().onBackClicked());
        save.setOnAction(_ -> viewModel().onSaveClicked());
    }
}
