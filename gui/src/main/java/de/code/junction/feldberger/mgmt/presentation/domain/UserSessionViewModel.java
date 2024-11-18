package de.code.junction.feldberger.mgmt.presentation.domain;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.UserSession;

public class UserSessionViewModel {

    private final IntegerProperty id;
    private final StringProperty username;

    public UserSessionViewModel(Integer id, String username) {

        this.id = new SimpleIntegerProperty(this, "id", id);
        this.username = new SimpleStringProperty(this, "username", username);
    }

    public UserSessionViewModel(User user) {

        this(user.getID(), user.getUsername());
    }

    public int getID() {

        return id.get();
    }

    public IntegerProperty idProperty() {

        return id;
    }

    public String getUsername() {

        return username.get();
    }

    public StringProperty usernameProperty() {

        return username;
    }

    public UserSession toUserSession() {

        return new UserSession(getID(), getUsername());
    }
}
