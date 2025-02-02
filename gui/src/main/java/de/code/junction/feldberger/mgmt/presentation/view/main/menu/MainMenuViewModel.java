package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavigator;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXViewModel;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainMenuViewModel extends FXViewModel {

    private final IntegerProperty userId;
    private final StringProperty username;
    private final ListProperty<Subview> subviews;
    private final ObjectProperty<Subview> selectedSubview;

    private final Navigator<ApplicationRoute> appNavigator;
    private final MainMenuNavigator subNavigator;

    public MainMenuViewModel(Messenger messenger,
                             Navigator<ApplicationRoute> appNavigator,
                             MainMenuNavigator subNavigator,
                             Integer userId,
                             String username) {

        super(messenger);
        this.appNavigator = appNavigator;
        this.subNavigator = subNavigator;

        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.selectedSubview = new SimpleObjectProperty<>();
        this.subviews = new SimpleListProperty<>(FXCollections.observableArrayList(Subview.values()));
    }

    @Override
    public void init() {
        selectedSubview.addListener(this::onSelectedSubviewChanged);
    }

    public void onLogoutClicked() {
        appNavigator.navigateTo(new ApplicationRoute.Login(getUsername()));
    }

    public void onSettingsClicked() {
        System.out.println("Settings...");
    }

    private void onSelectedSubviewChanged(Observable observable, Subview oldValue, Subview newValue) {

        switch (newValue) {
            case CUSTOMERS -> subNavigator.navigateTo(new MainMenuRoute.CustomerOverview());
            default -> System.out.println("Not customers");
        }
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public ObservableList<Subview> getSubviews() {
        return subviews.get();
    }

    public ListProperty<Subview> subviewsProperty() {
        return subviews;
    }

    public Subview getSelectedSubview() {
        return selectedSubview.get();
    }

    public ObjectProperty<Subview> selectedSubviewProperty() {
        return selectedSubview;
    }

    public MainMenuNavigator getSubNavigator() {
        return subNavigator;
    }
}
