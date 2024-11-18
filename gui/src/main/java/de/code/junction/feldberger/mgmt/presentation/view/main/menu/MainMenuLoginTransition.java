package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.LoginForm;
import de.code.junction.feldberger.mgmt.presentation.components.jfx.ApplicationNavRoute.UserSession;
import de.code.junction.feldberger.mgmt.presentation.components.navigation.BypassTransition;

import java.util.function.Consumer;

public class MainMenuLoginTransition implements BypassTransition<UserSession, LoginForm> {

    private final Consumer<LoginForm> onEnd;

    public MainMenuLoginTransition(Consumer<LoginForm> onEnd) {

        this.onEnd = onEnd;
    }

    @Override
    public LoginForm convert(UserSession session) {

        return new LoginForm(session.username());
    }

    @Override
    public void end(LoginForm form) {

        onEnd.accept(form);
    }
}
