package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class CustomerEditorController extends FXController {

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

    private void onBackClicked(ActionEvent event) {

        onBackClicked.run();
    }

    private void onSaveClicked(ActionEvent event) {

        onSaveClicked.accept(viewModel.toCustomer());
    }
}
