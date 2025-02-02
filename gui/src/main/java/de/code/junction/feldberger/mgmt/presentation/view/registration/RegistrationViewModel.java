package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.components.application.ApplicationRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXViewModel;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.WorkerStateEvent;

import java.util.regex.Pattern;

public class RegistrationViewModel extends FXViewModel {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty repeatPassword;
    private final BooleanProperty submitDisabled;

    private final Navigator<ApplicationRoute> navigator;
    private final RegistrationService registrationService;

    public RegistrationViewModel(Messenger messenger,
                                 Navigator<ApplicationRoute> navigator,
                                 RegistrationService registrationService,
                                 String username) {

        super(messenger);

        this.username = new SimpleStringProperty(username);
        this.navigator = navigator;
        this.registrationService = registrationService;

        password = new SimpleStringProperty("");
        repeatPassword = new SimpleStringProperty("");
        submitDisabled = new SimpleBooleanProperty(false);
    }

    @Override
    public void init() {

        submitDisabled.bind(registrationService.runningProperty());

        registrationService.usernameProperty().bind(username);
        registrationService.passwordProperty().bind(password);
        registrationService.repeatPasswordProperty().bind(repeatPassword);

        username.addListener(this::onUsernameChanged);
        registrationService.setOnSucceeded(this::onRegistrationServiceSucceeded);
    }

    public void onBackClicked() {
        navigator.navigateTo(new ApplicationRoute.Login(getUsername()));
    }

    public void onSubmitClicked() {
        registrationService.restart();
    }

    private void onRegistrationServiceSucceeded(WorkerStateEvent event) {

        switch (registrationService.getValue()) {
            case RegistrationResult.Success(User user) -> {

                final var username = user.getUsername();
                final ApplicationRoute route;

                if (user.isInactive()) {
                    messenger().send(Messages.REGISTRATION_SUCCEEDED_USER_INACTIVE);
                    route = new ApplicationRoute.Login(username);
                } else {
                    route = new ApplicationRoute.MainMenu(user.getId(), username);
                }

                navigator.navigateTo(route);
            }

            case RegistrationResult.Failure(Message message) -> messenger().send(message);
        }
    }

    private void onUsernameChanged(Observable observable, String oldValue, String newValue) {

        final var matcher = WHITESPACE_PATTERN.matcher(newValue);
        if (matcher.find()) {
            username.set(oldValue);
        }
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

    public StringProperty repeatPasswordProperty() {
        return repeatPassword;
    }

    public BooleanProperty submitDisabledProperty() {
        return submitDisabled;
    }
}
