package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.presentation.model.Credentials;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {

    public final StringProperty username;
    public final StringProperty password;

    public LoginViewModel(String username) {

        this(username, "");
    }

    public LoginViewModel(String username, String password) {

        this.username = new SimpleStringProperty(this, "username", username);
        this.password = new SimpleStringProperty(this, "password", password);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public Credentials toCredentials() {

        return new Credentials(getUsername().trim(), getPassword());
    }
}
