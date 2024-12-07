package de.code.junction.feldberger.mgmt.presentation.components.application;

public sealed interface ApplicationNavRoute {

    record RegistrationForm(
            String username,
            String password,
            String repeatPassword
    ) implements ApplicationNavRoute {

        public RegistrationForm(String username) {

            this(username, "", "");
        }
    }

    record LoginForm(
            String username,
            String password
    ) implements ApplicationNavRoute {

        public LoginForm(String username) {

            this(username, "");
        }

    }

    record UserSession(
            int userId,
            String username
    ) implements ApplicationNavRoute {
    }
}
