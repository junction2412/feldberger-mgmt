package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.presentation.components.common.NavigatorFactory;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginViewModel;

public class ViewModelFactory {

    private final Messenger messenger;
    private final NavigatorFactory navigatorFactory;
    private final ServiceFactory serviceFactory;

    public ViewModelFactory(Messenger messenger, NavigatorFactory navigatorFactory, ServiceFactory serviceFactory) {

        this.messenger = messenger;
        this.navigatorFactory = navigatorFactory;
        this.serviceFactory = serviceFactory;
    }

    public LoginViewModel login(String username) {
        return new LoginViewModel(messenger, navigatorFactory.application(), serviceFactory.loginService(), username);
    }
}
