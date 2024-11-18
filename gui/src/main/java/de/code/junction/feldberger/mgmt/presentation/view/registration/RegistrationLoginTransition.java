package de.code.junction.feldberger.mgmt.presentation.view.registration;

import de.code.junction.feldberger.mgmt.presentation.components.navigation.BypassTransition;

import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.LoginForm;
import static de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.RegistrationForm;

public class RegistrationLoginTransition implements BypassTransition<RegistrationForm, LoginForm> {

    private final Consumer<LoginForm> onEnd;

    public RegistrationLoginTransition(Consumer<LoginForm> onEnd) {

        this.onEnd = onEnd;
    }

    @Override
    public LoginForm convert(RegistrationForm registrationForm) {

        return new LoginForm(registrationForm.username());
    }

    @Override
    public void end(LoginForm loginForm) {

        onEnd.accept(loginForm);
    }
}
