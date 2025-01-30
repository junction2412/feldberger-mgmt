package de.code.junction.feldberger.mgmt.presentation.view.registration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RegistrationService extends Service<RegistrationResult> {

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty repeatPassword;

    private final PerformRegistration performRegistration;

    public RegistrationService(PerformRegistration performRegistration) {

        this.performRegistration = performRegistration;

        username = new SimpleStringProperty("");
        password = new SimpleStringProperty("");
        repeatPassword = new SimpleStringProperty("");
    }

    @Override
    protected Task<RegistrationResult> createTask() {

        return new Task<>() {

            @Override
            protected RegistrationResult call() {

                return performRegistration.submit(getUsername(), getPassword(), getRepeatPassword());
            }
        };
    }

    public String getRepeatPassword() {
        return repeatPassword.get();
    }

    public StringProperty repeatPasswordProperty() {
        return repeatPassword;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }
}
