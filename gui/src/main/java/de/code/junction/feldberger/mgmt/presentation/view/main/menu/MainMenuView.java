package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.view.FXView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getLabelStringResources;

public class MainMenuView extends FXView<MainMenuViewModel> {

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

    public MainMenuView(MainMenuViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public String fxml() {
        return "main-menu-view.fxml";
    }

    @Override
    public void initialize() {

        viewModel().getSubNavigator().setScope(subview);

        navigation.setCellFactory(_ -> new ListCell<>() {

            @Override
            protected void updateItem(Subview subview, boolean empty) {

                super.updateItem(subview, empty);

                final var text = (!empty)
                        ? getLabelStringResources().getString(subview.getLabelKey())
                        : "";

                setText(text);
            }
        });

        userId.textProperty().bind(viewModel().userIdProperty().asString());
        username.textProperty().bind(viewModel().usernameProperty());
        navigation.itemsProperty().bind(viewModel().subviewsProperty());
        viewModel().selectedSubviewProperty().bind(navigation.getSelectionModel().selectedItemProperty());

        logout.setOnAction(_ -> viewModel().onLogoutClicked());
        settings.setOnAction(_ -> viewModel().onSettingsClicked());
    }
}
