package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginView;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationView;

public class ViewFactory {

    private final ViewModelFactory viewModelFactory;

    public ViewFactory(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }

    public FXLoadable login(String username) {
        return new LoginView(viewModelFactory.login(username));
    }

    public FXLoadable registration(String username) {
        return new RegistrationView(viewModelFactory.registration(username));
    }
}
