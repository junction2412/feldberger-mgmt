package de.code.junction.feldberger.mgmt.presentation.view.registration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegistrationFormViewModel {

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty repeatPassword;

    public RegistrationFormViewModel(String username) {

        this.username = new SimpleStringProperty(this, "username", username);
        password = new SimpleStringProperty(this, "password", "");
        repeatPassword = new SimpleStringProperty(this, "repeatPassword", "");
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

    public String getPassword() {

        return password.get();
    }

    public StringProperty passwordProperty() {

        return password;
    }

    public void setPassword(String password) {

        this.password.set(password);
    }

    public String getRepeatPassword() {

        return repeatPassword.get();
    }

    public StringProperty repeatPasswordProperty() {

        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {

        this.repeatPassword.set(repeatPassword);
    }

    public RegistrationForm toRegistrationForm() {

        return new RegistrationForm(
                getUsername().trim(),
                getPassword(),
                getRepeatPassword()
        );
    }
}
