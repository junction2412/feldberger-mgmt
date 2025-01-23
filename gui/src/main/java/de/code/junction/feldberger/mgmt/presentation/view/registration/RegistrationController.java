package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class RegistrationController extends FXController {

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

        back.setOnAction(this::onBackClicked);
        submit.setOnAction(this::onSubmitClicked);

        username.textProperty().bindBidirectional(viewModel.usernameProperty());
        viewModel.passwordProperty().bind(Bindings.createStringBinding(
                () -> password.getCharacters().toString(),
                password.textProperty()
        ));

        viewModel.repeatPasswordProperty().bind(Bindings.createStringBinding(
                () -> repeatPassword.getCharacters().toString(),
                repeatPassword.textProperty()
        ));
    }

    private void onBackClicked(ActionEvent event) {

        onBackClicked.accept(viewModel.getUsername());
    }

    private void onSubmitClicked(ActionEvent event) {

        onSubmitClicked.accept(viewModel.toRegistrationForm());
    }
}
