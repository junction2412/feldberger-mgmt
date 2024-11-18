package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.UserSession;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.domain.UserSessionViewModel;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class MainMenuController extends FXController {

    private final TransitionOrchestrator<UserSession, LoginForm> logoutTransition;
    private final TransitionOrchestrator<UserSession, UserSession> settingsTransition;
    private final UserSessionViewModel viewModel;

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

    public MainMenuController(TransitionOrchestrator<UserSession, LoginForm> logoutTransition,
                              TransitionOrchestrator<UserSession, UserSession> settingsTransition,
                              UserSessionViewModel viewModel) {

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

        logoutTransition.orchestrate(viewModel.toUserSession());
    }

    private void onSettingsClicked(ActionEvent event) {

        settingsTransition.orchestrate(viewModel.toUserSession());
    }
}
