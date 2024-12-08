package de.code.junction.feldberger.mgmt.presentation.view.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginFormViewModel {

    public final StringProperty username;
    public final StringProperty password;

    public LoginFormViewModel(String username) {

        this(
                username,
                ""
        );
    }

    public LoginFormViewModel(String username,
                              String password) {

        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
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

    public LoginForm toLoginForm() {

        return new LoginForm(getUsername().trim(), getPassword());
    }
}
