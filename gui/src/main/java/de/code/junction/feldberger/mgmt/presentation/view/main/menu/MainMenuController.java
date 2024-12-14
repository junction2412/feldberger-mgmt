package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.common.NavContextProvider;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
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
import java.util.HashMap;
import java.util.ResourceBundle;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getLabelStringResources;

public final class MainMenuController extends FXController {

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
    private ListView<Subview> navigation;
    @FXML
    private AnchorPane subview;

    private final Transition<Void, ?> logoutTransition;
    private final Transition<UserSession, ?> settingsTransition;
    private final MainMenuViewModel viewModel;

    public MainMenuController(Transition<Void, ?> logoutTransition,
                              Transition<UserSession, ?> settingsTransition,
                              MainMenuViewModel viewModel) {

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
        subview.getChildren().addListener(this::onSubviewChildrenChanged);

        navigation.setCellFactory(_ -> new ListCell<>() {

            @Override
            protected void updateItem(Subview subview, boolean empty) {

                super.updateItem(subview, empty);

                final String text;

                if (empty)
                    text = "";
                else
                    text = getLabelStringResources().getString(subview.getLabelKey());

                setText(text);
            }
        });

        userID.textProperty().bind(viewModel.userIdProperty().asString());
        username.textProperty().bind(viewModel.usernameProperty());

        final var subviews = Arrays.asList(Subview.values());

        final var selectedSubview = viewModel.getSelectedSubview();
        navigation.getItems().setAll(subviews);
        navigation.getSelectionModel().select(selectedSubview);
        viewModel.selectedSubviewProperty().bind(navigation.getSelectionModel().selectedItemProperty());
        navigation.getSelectionModel().selectedItemProperty().addListener(this::onNavigationSelectionChanged);
    }

    @Override
    protected void translate(ResourceBundle bundle) {

        userIDLabel.setText(bundle.getString("view.main_menu.user.id"));
        usernameLabel.setText(bundle.getString("view.main_menu.username"));
        logout.setText(bundle.getString("view.main_menu.logout"));
        settings.setText(bundle.getString("view.main_menu.settings"));
    }

    private void onLogoutClicked(ActionEvent event) {

        logoutTransition.orchestrate(null);
    }

    private void onSettingsClicked(ActionEvent event) {

        settingsTransition.orchestrate(viewModel.toUserSession());
    }

    private void onNavigationSelectionChanged(Observable observable,
                                              Subview oldValue,
                                              Subview newValue) {

        final var navContextProvider = NavContextProvider.getInstance();
        // always create a new context from scratch to ensure an empty stack
        final var navContext = navContextProvider.mainMenu(viewModel.getUserId());
        navContext.setScope(subview);

        switch (newValue) {
            case CUSTOMERS -> navContext.push(new Route<>(
                    MainMenuRoute.CUSTOMER_OVERVIEW,
                    new HashMap<>()
            ));
            default -> {
            }
        }
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
