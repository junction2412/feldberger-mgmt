package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManager;
import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
import de.code.junction.feldberger.mgmt.presentation.view.Controller;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class LoginController extends Controller {

    private final TransitionManager<Credentials> mainMenuTransitionManager;
    private final TransitionManager<String> registrationTransitionManager;

    private final LoginViewModel viewModel;

    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;

    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button register;
    @FXML
    private Button submit;

    public LoginController(TransitionManager<Credentials> mainMenuTransitionManager,
                           TransitionManager<String> registrationTransitionManager,
                           LoginViewModel viewModel) {

        super("login-view.fxml");
        this.mainMenuTransitionManager = mainMenuTransitionManager;
        this.registrationTransitionManager = registrationTransitionManager;
        this.viewModel = viewModel;
    }

    @Override
    protected void initialize() {

        usernameLabel.setLabelFor(usernameField);
        passwordLabel.setLabelFor(passwordField);

        register.setOnAction(this::onRegisterClicked);
        submit.setOnAction(this::onSubmitClicked);

        usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
        viewModel.passwordProperty().bind(Bindings.createStringBinding(() -> passwordField.getCharacters().toString(),
                passwordField.textProperty()));
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        usernameLabel.setText(bundle.getString("view.login.username"));
        passwordLabel.setText(bundle.getString("view.login.password"));
        register.setText(bundle.getString("view.login.register"));
        submit.setText(bundle.getString("view.login.submit"));
    }

    private void onSubmitClicked(ActionEvent event) {

        mainMenuTransitionManager.transition(viewModel.toCredentials());
    }

    private void onRegisterClicked(ActionEvent event) {

        final String username = usernameField.getText();

        registrationTransitionManager.transition(username);
    }
}
