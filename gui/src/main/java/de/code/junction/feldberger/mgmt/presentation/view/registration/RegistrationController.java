package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.model.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class RegistrationController extends FXController {

    private final TransitionOrchestrator<RegistrationForm, User> registrationTransition;
    private final TransitionOrchestrator<String, String> loginTransition;
    private final RegistrationFormViewModel viewModel;

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

    public RegistrationController(TransitionOrchestrator<RegistrationForm, User> registrationTransition,
                                  TransitionOrchestrator<String, String> loginTransition,
                                  RegistrationFormViewModel viewModel) {

        super("registration-view.fxml");

        this.registrationTransition = registrationTransition;
        this.loginTransition = loginTransition;
        this.viewModel = viewModel;
    }

    @Override
    protected void initialize() {

        usernameLabel.setLabelFor(usernameField);
        passwordLabel.setLabelFor(passwordField);
        repeatPasswordLabel.setLabelFor(repeatPasswordField);

        back.setOnAction(this::onBackClicked);
        submit.setOnAction(this::onSubmitClicked);

        usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
        viewModel.passwordProperty().bind(Bindings.createStringBinding(() -> passwordField.getCharacters().toString(),
                passwordField.textProperty()));

        viewModel.repeatPasswordProperty().bind(Bindings.createStringBinding(
                () -> repeatPasswordField.getCharacters().toString(),
                repeatPasswordLabel.textProperty()
        ));
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        usernameLabel.setText(bundle.getString("view.registration.username"));
        passwordLabel.setText(bundle.getString("view.registration.password"));
        repeatPasswordLabel.setText(bundle.getString("view.registration.repeat.password"));
        back.setText(bundle.getString("view.registration.back"));
        submit.setText(bundle.getString("view.registration.submit"));
    }

    private void onBackClicked(ActionEvent event) {

        loginTransition.orchestrate(usernameField.getText());
    }

    private void onSubmitClicked(ActionEvent event) {

        registrationTransition.orchestrate(viewModel.toRegistrationForm());
    }
}
