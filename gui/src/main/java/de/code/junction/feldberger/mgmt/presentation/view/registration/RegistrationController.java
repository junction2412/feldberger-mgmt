package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class RegistrationController extends FXController {

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;

    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label repeatPasswordLabel;
    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button back;
    @FXML
    private Button submit;

    private final Consumer<String> onBackClicked;
    private final Consumer<RegistrationForm> onSubmitClicked;

    private final RegistrationFormViewModel viewModel;

    public RegistrationController(RegistrationFormViewModel viewModel,
                                  Consumer<String> onBackClicked,
                                  Consumer<RegistrationForm> onSubmitClicked) {

        super("registration-view.fxml");

        this.viewModel = viewModel;
        this.onBackClicked = onBackClicked;
        this.onSubmitClicked = onSubmitClicked;
    }

    @Override
    protected void initialize() {

        usernameLabel.setLabelFor(usernameField);
        passwordLabel.setLabelFor(passwordField);
        repeatPasswordLabel.setLabelFor(repeatPasswordField);

        back.setOnAction(this::onBackClicked);
        submit.setOnAction(this::onSubmitClicked);

        usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
        viewModel.passwordProperty().bind(Bindings.createStringBinding(
                () -> passwordField.getCharacters().toString(),
                passwordField.textProperty()
        ));

        viewModel.repeatPasswordProperty().bind(Bindings.createStringBinding(
                () -> repeatPasswordField.getCharacters().toString(),
                repeatPasswordLabel.textProperty()
        ));
    }

    @Override
    protected void translate(ResourceBundle bundle) {
    }

    private void onBackClicked(ActionEvent event) {

        onBackClicked.accept(viewModel.getUsername());
    }

    private void onSubmitClicked(ActionEvent event) {

        onSubmitClicked.accept(viewModel.toRegistrationForm());
    }
}
