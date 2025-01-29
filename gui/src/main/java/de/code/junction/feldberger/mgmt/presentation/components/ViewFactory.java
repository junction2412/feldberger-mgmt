package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginView;

public class ViewFactory {

    private final ViewModelFactory viewModelFactory;

    public ViewFactory(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }

    public FXLoadable login(String username) {
        return new LoginView(viewModelFactory.login(username));
    }
}
