package de.code.junction.feldberger.mgmt.presentation.view.login;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.BypassTransition;

import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.LoginForm;
import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.RegistrationForm;

public class LoginRegistrationTransition implements BypassTransition<LoginForm, RegistrationForm> {

    private final Consumer<RegistrationForm> onEnd;

    public LoginRegistrationTransition(Consumer<RegistrationForm> onEnd) {

        this.onEnd = onEnd;
    }

    @Override
    public RegistrationForm convert(LoginForm loginForm) {

        return new RegistrationForm(loginForm.username());
    }

    @Override
    public void end(RegistrationForm registrationForm) {

        onEnd.accept(registrationForm);
    }
}
