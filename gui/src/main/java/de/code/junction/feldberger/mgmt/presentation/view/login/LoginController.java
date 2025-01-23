package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class LoginController extends FXController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

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

        register.setOnAction(this::onRegisterClicked);
        submit.setOnAction(this::onSubmitClicked);

        username.textProperty().bindBidirectional(viewModel.usernameProperty());
        viewModel.passwordProperty().bind(Bindings.createStringBinding(
                () -> password.getCharacters().toString(),
                password.textProperty()
        ));
    }

    private void onSubmitClicked(ActionEvent event) {

        onSubmitClicked.accept(viewModel.toLoginForm());
    }

    private void onRegisterClicked(ActionEvent event) {

        onRegisterClicked.accept(viewModel.getUsername());
    }
}
