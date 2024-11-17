package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionLifecycleOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionManager;
import de.code.junction.feldberger.mgmt.presentation.model.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class LoginController extends FXController {

    private final TransitionManager<LoginForm> mainMenuTransitionManager;
    private final TransitionLifecycleOrchestrator<String, String> registrationTransition;

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

    public LoginController(TransitionManager<LoginForm> mainMenuTransitionManager,
                           TransitionLifecycleOrchestrator<String, String> registrationTransition,
                           LoginViewModel viewModel) {

        super("login-view.fxml");
        this.mainMenuTransitionManager = mainMenuTransitionManager;
        this.registrationTransition = registrationTransition;
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

        registrationTransition.orchestrate(username);
    }
}
