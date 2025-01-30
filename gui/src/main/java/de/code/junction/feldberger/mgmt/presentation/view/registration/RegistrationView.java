package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationView extends FXView<RegistrationViewModel> {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;

    @FXML
    private Button back;
    @FXML
    private Button submit;

    public RegistrationView(RegistrationViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "registration-view.fxml";
    }

    @Override
    public void initialize() {

        /*
         * username input validation wouldn't work properly with bidirectional binding
         * so this is how I fixed it
         */
        username.setText(viewModel().getUsername());
        username.textProperty().addListener((_, _, name) -> viewModel().setUsername(name));
        viewModel().usernameProperty().addListener((_, _, name) -> {
            if (name.equals(username.getText())) return;

            username.setText(name);
            username.positionCaret(name.length());
        });

        viewModel().passwordProperty().bind(Bindings.createStringBinding(
                () -> password.getCharacters().toString(),
                password.textProperty()
        ));

        viewModel().repeatPasswordProperty().bind(Bindings.createStringBinding(
                () -> repeatPassword.getCharacters().toString(),
                repeatPassword.textProperty()
        ));

        submit.disableProperty().bind(viewModel().submitDisabledProperty());

        back.setOnAction(_ -> viewModel().onBackClicked());
        submit.setOnAction(_ -> viewModel().onSubmitClicked());
    }
}
