package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.domain.UserViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class MainMenuController extends FXController {

    private final TransitionOrchestrator<String, String> logoutTransition;
    private final TransitionOrchestrator<UserViewModel, UserViewModel> settingsTransition;
    private final UserViewModel viewModel;

    @FXML
    private Label userIDLabel;
    @FXML
    private Label userID;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label username;

    @FXML
    private Button logout;
    @FXML
    private Button settings;

    public MainMenuController(TransitionOrchestrator<String, String> logoutTransition,
                              TransitionOrchestrator<UserViewModel, UserViewModel> settingsTransition,
                              UserViewModel viewModel) {

        super("main-menu-view.fxml");

        this.logoutTransition = logoutTransition;
        this.settingsTransition = settingsTransition;
        this.viewModel = viewModel;
    }

    @Override
    protected void initialize() {

        userIDLabel.setLabelFor(userID);
        usernameLabel.setLabelFor(username);

        logout.setOnAction(this::onLogoutClicked);
        settings.setOnAction(this::onSettingsClicked);

        userID.textProperty().bind(viewModel.idProperty().asString());
        username.textProperty().bind(viewModel.usernameProperty());
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        userIDLabel.setText(bundle.getString("view.main.menu.user.id"));
        usernameLabel.setText(bundle.getString("view.main.menu.username"));
        logout.setText(bundle.getString("view.main.menu.logout"));
        settings.setText(bundle.getString("view.main.menu.settings"));
    }

    private void onLogoutClicked(ActionEvent event) {

        logoutTransition.orchestrate(viewModel.getUsername());
    }

    private void onSettingsClicked(ActionEvent event) {

        settingsTransition.orchestrate(viewModel);
    }
}
