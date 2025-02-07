package de.code.junction.feldberger.mgmt.presentation.components;

import de.code.junction.feldberger.mgmt.data.access.PersistenceManager;
import de.code.junction.feldberger.mgmt.presentation.view.customer.overview.CustomerListService;
import de.code.junction.feldberger.mgmt.presentation.view.login.LoginService;
import de.code.junction.feldberger.mgmt.presentation.view.login.PerformLogin;
import de.code.junction.feldberger.mgmt.presentation.view.registration.PerformRegistration;
import de.code.junction.feldberger.mgmt.presentation.view.registration.RegistrationService;

public class ServiceFactory {

    private final PersistenceManager persistenceManager;

    public ServiceFactory(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public LoginService loginService() {
        return new LoginService(new PerformLogin(persistenceManager.userDao()));
    }

    public RegistrationService registrationService() {
        return new RegistrationService(new PerformRegistration(persistenceManager.userDao()));
    }

    public CustomerListService customerListService() {
        return new CustomerListService(persistenceManager.customerDao());
    }
}
