package de.code.junction.feldberger.mgmt.presentation.view.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoginService extends Service<LoginResult> {

    private final StringProperty username;
    private final StringProperty password;

    /// actual business logic
    private final PerformLogin performLogin;

    public LoginService(PerformLogin performLogin) {

        username = new SimpleStringProperty("");
        password = new SimpleStringProperty("");

        this.performLogin = performLogin;
    }


    @Override
    protected Task<LoginResult> createTask() {

        return new Task<>() {

            @Override
            protected LoginResult call() {

                return performLogin.submit(getUsername(), getPassword());
            }
        };
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
}
