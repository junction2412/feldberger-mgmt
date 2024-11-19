package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.UserSession;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavContext;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavRoute;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionOrchestrator;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.ResourceBundle;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil.getLabelStringResources;

public final class MainMenuController extends FXController {

    private final TransitionOrchestrator<UserSession, LoginForm> logoutTransition;
    private final TransitionOrchestrator<UserSession, UserSession> settingsTransition;
    private final UserSessionViewModel viewModel;

    private final MainMenuNavContext navContext;

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

    @FXML
    private ListView<MainMenuNavRoute.Subview> navigation;
    @FXML
    private AnchorPane subview;

    public MainMenuController(TransitionOrchestrator<UserSession, LoginForm> logoutTransition,
                              TransitionOrchestrator<UserSession, UserSession> settingsTransition,
                              UserSessionViewModel viewModel,
                              MainMenuNavContext navContext) {

        super("main-menu-view.fxml");

        this.logoutTransition = logoutTransition;
        this.settingsTransition = settingsTransition;
        this.viewModel = viewModel;
        this.navContext = navContext;
    }

    @Override
    protected void initialize() {

        navContext.setParent(subview);

        userIDLabel.setLabelFor(userID);
        usernameLabel.setLabelFor(username);

        logout.setOnAction(this::onLogoutClicked);
        settings.setOnAction(this::onSettingsClicked);
        navigation.getSelectionModel().selectedItemProperty().addListener(this::onNavigationSelectionChanged);
        subview.getChildren().addListener(this::onSubviewChildrenChanged);

        navigation.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(MainMenuNavRoute.Subview subview, boolean empty) {
                super.updateItem(subview, empty);

                if (!empty) {
                    final ResourceBundle bundle = getLabelStringResources();

                    setText(bundle.getString(subview.getLabelKey()));
                } else
                    setText("");
            }
        });

        userID.textProperty().bind(viewModel.userIDProperty().asString());
        username.textProperty().bind(viewModel.usernameProperty());

        final var subviews = Arrays.stream(MainMenuNavRoute.Subview.values())
                .filter(subview -> subview != MainMenuNavRoute.Subview.NONE) // Exclude Subview.NONE
                .toList();

        navigation.getItems().setAll(subviews);
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

    private void onNavigationSelectionChanged(Observable observable, MainMenuNavRoute.Subview oldValue, MainMenuNavRoute.Subview newValue) {

        navContext.navigateTo(new MainMenuNavRoute(viewModel.getUserID(), newValue));
    }

    private void onSubviewChildrenChanged(ListChangeListener.Change<? extends Node> change) {

        anchorChild(change.getList().getFirst());
    }

    private static void anchorChild(Node child) {

        if (child == null)
            return;

        AnchorPane.setTopAnchor(child, 0.0);
        AnchorPane.setRightAnchor(child, 0.0);
        AnchorPane.setBottomAnchor(child, 0.0);
        AnchorPane.setLeftAnchor(child, 0.0);
    }
}
