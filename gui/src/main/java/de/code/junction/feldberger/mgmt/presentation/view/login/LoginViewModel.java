package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.WorkerStateEvent;

public class LoginViewModel extends FXViewModel {

    private final StringProperty username;
    private final StringProperty password;
    private final BooleanProperty submitDisabled;

    private final Navigator<ApplicationRoute> navigator;
    private final LoginService loginService;

    public LoginViewModel(Messenger messenger,
                          Navigator<ApplicationRoute> navigator,
                          LoginService loginService,
                          String username) {

        super(messenger);

        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty("");
        this.submitDisabled = new SimpleBooleanProperty(false);

        this.navigator = navigator;
        this.loginService = loginService;
    }

    @Override
    public void init() {

        submitDisabled.bind(loginService.runningProperty());

        loginService.usernameProperty().bindBidirectional(username);
        loginService.passwordProperty().bindBidirectional(password);
        loginService.setOnSucceeded(this::onLoginServiceSucceeded);
    }

    public void onSubmitClicked() {
        loginService.restart();
    }

    public void onRegisterClicked() {
        navigator.navigateTo(new ApplicationRoute.Registration(getUsername()));
    }

    private void onLoginServiceSucceeded(WorkerStateEvent event) {

        switch (loginService.getValue()) {
            case LoginResult.Success(User user) -> navigator.navigateTo(new ApplicationRoute.MainMenu(
                    user.getId(),
                    user.getUsername()
            ));
            case LoginResult.Failure(Message message) -> messenger().send(message);
        }
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

    public BooleanProperty submitDisabledProperty() {
        return submitDisabled;
    }
}
