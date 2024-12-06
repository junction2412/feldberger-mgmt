package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import javafx.beans.property.*;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.UserSession;

public class MainMenuViewModel {

    private final IntegerProperty userId;
    private final StringProperty username;
    private final ObjectProperty<Subview> selectedSubview;

    public MainMenuViewModel(Integer userId,
                             String username,
                             Subview selectedSubview) {

        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.selectedSubview = new SimpleObjectProperty<>(selectedSubview);
    }

    public int getUserId() {

        return userId.get();
    }

    public IntegerProperty userIdProperty() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId.set(userId);
    }

    public String getUsername() {

        return username.get();
    }

    public StringProperty usernameProperty() {

        return username;
    }

    public void setUsername(String username) {

        this.username.set(username);
    }

    public Subview getSelectedSubview() {

        return selectedSubview.get();
    }

    public ObjectProperty<Subview> selectedSubviewProperty() {

        return selectedSubview;
    }

    public void setSelectedSubview(Subview selectedSubview) {

        this.selectedSubview.set(selectedSubview);
    }

    public UserSession toUserSession() {

        return new UserSession(getUserId(), getUsername());
    }
}
