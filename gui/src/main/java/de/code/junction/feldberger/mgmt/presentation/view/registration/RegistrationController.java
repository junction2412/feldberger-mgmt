package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.RegistrationForm;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.UserSession;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
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

    private final TransitionOrchestrator<RegistrationForm, UserSession> registrationTransition;
    private final TransitionOrchestrator<RegistrationForm, LoginForm> loginTransition;
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

    public RegistrationController(TransitionOrchestrator<RegistrationForm, UserSession> registrationTransition,
                                  TransitionOrchestrator<RegistrationForm, LoginForm> loginTransition,
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

        loginTransition.orchestrate(viewModel.toRegistrationForm());
    }

    private void onSubmitClicked(ActionEvent event) {

        registrationTransition.orchestrate(viewModel.toRegistrationForm());
    }
}
