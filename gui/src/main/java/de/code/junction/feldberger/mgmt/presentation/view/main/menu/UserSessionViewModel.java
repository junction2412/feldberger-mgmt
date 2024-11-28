package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationNavRoute.UserSession;

public class UserSessionViewModel {

    private final IntegerProperty userID;
    private final StringProperty username;

    public UserSessionViewModel(Integer userID, String username) {

        this.userID = new SimpleIntegerProperty(this, "id", userID);
        this.username = new SimpleStringProperty(this, "username", username);
    }

    public UserSessionViewModel(User user) {

        this(user.getId(), user.getUsername());
    }

    public int getUserID() {

        return userID.get();
    }

    public IntegerProperty userIDProperty() {

        return userID;
    }

    public String getUsername() {

        return username.get();
    }

    public StringProperty usernameProperty() {

        return username;
    }

    public UserSession toUserSession() {

        return new UserSession(getUserID(), getUsername());
    }
}
