package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginService;
import de.code.junction.feldberger.mgmt.presentation.view.login.PerformLogin;

public class ServiceFactory {

    private final PersistenceManager persistenceManager;

    public ServiceFactory(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public LoginService loginService() {
        return new LoginService(new PerformLogin(persistenceManager.userDao()));
    }
}
