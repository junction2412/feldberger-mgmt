package de.code.junction.feldberger.mgmt.presentation.view.login;

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

public class LoginController extends FXController {

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

    private final Consumer<LoginForm> onSubmitClicked;
    private final Consumer<String> onRegisterClicked;

    private final LoginViewModel viewModel;

    public LoginController(LoginViewModel viewModel,
                           Consumer<LoginForm> onSubmitClicked,
                           Consumer<String> onRegisterClicked) {

        super("login-view.fxml");

        this.viewModel = viewModel;
        this.onSubmitClicked = onSubmitClicked;
        this.onRegisterClicked = onRegisterClicked;
    }

    @Override
    protected void initialize() {

        usernameLabel.setLabelFor(usernameField);
        passwordLabel.setLabelFor(passwordField);

        register.setOnAction(this::onRegisterClicked);
        submit.setOnAction(this::onSubmitClicked);

        usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
        viewModel.passwordProperty().bind(Bindings.createStringBinding(
                () -> passwordField.getCharacters().toString(),
                passwordField.textProperty()
        ));
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        usernameLabel.setText(bundle.getString("view.login.username"));
        passwordLabel.setText(bundle.getString("view.login.password"));
        register.setText(bundle.getString("view.login.register"));
        submit.setText(bundle.getString("view.login.submit"));
    }

    private void onSubmitClicked(ActionEvent event) {

        onSubmitClicked.accept(viewModel.toLoginForm());
    }

    private void onRegisterClicked(ActionEvent event) {

        onRegisterClicked.accept(viewModel.getUsername());
    }
}
