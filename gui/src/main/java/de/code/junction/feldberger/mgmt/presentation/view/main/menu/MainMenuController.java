package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.common.NavigatorFactory;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.CustomerOverview;
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
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getLabelStringResources;

public final class MainMenuController extends FXController {

    @FXML
    private Label userId;
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

    private final Runnable onLogoutClicked;
    private final Consumer<UserSession> onSettingsClicked;

    private final MainMenuViewModel viewModel;

    public MainMenuController(MainMenuViewModel viewModel,
                              Runnable onLogoutClicked,
                              Consumer<UserSession> onSettingsClicked) {

        super("main-menu-view.fxml");

        this.viewModel = viewModel;

        this.onSettingsClicked = onSettingsClicked;
        this.onLogoutClicked = onLogoutClicked;
    }

    @Override
    public void initialize() {

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

        userId.textProperty().bind(viewModel.userIdProperty().asString());
        username.textProperty().bind(viewModel.usernameProperty());

        final var subviews = Arrays.asList(Subview.values());
        final var selectedSubview = viewModel.getSelectedSubview();

        navigation.getItems().setAll(subviews);
        navigation.getSelectionModel().select(selectedSubview);
        viewModel.selectedSubviewProperty().bind(navigation.getSelectionModel().selectedItemProperty());
        navigation.getSelectionModel().selectedItemProperty().addListener(this::onNavigationSelectionChanged);

        NavigatorFactory.getInstance().initMainMenu(subview);
    }

    private void onLogoutClicked(ActionEvent event) {

        onLogoutClicked.run();
    }

    private void onSettingsClicked(ActionEvent event) {

        onSettingsClicked.accept(viewModel.toUserSession());
    }

    private void onNavigationSelectionChanged(Observable observable, Subview oldValue, Subview newValue) {

        final var navContextProvider = NavigatorFactory.getInstance();
        // always create a new context from scratch to ensure an empty stack
        final var navContext = navContextProvider.mainMenu();
        navContext.setScope(subview);

        switch (newValue) {
            case CUSTOMERS -> navContext.navigateTo(new CustomerOverview());
            default -> {
            }
        }
    }

    private void onSubviewChildrenChanged(ListChangeListener.Change<? extends Node> change) {

        anchorChild(change.getList().getFirst());
    }

    private static void anchorChild(Node child) {

        if (child == null) return;

        AnchorPane.setTopAnchor(child, 0.0);
        AnchorPane.setRightAnchor(child, 0.0);
        AnchorPane.setBottomAnchor(child, 0.0);
        AnchorPane.setLeftAnchor(child, 0.0);
    }
}
