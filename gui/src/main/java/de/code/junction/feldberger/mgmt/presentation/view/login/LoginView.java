package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView extends FXView<LoginViewModel> {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private Button register;
    @FXML
    private Button submit;

    public LoginView(LoginViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "login-view.fxml";
    }

    @Override
    public void initialize() {

        username.textProperty().bindBidirectional(viewModel().usernameProperty());
        viewModel().passwordProperty().bind(Bindings.createStringBinding(
                () -> password.getCharacters().toString(),
                password.textProperty()
        ));

        submit.disableProperty().bind(viewModel().submitDisabledProperty());

        register.setOnAction(_ -> viewModel().onRegisterClicked());
        submit.setOnAction(_ -> viewModel().onSubmitClicked());
    }
}
