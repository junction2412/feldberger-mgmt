package de.code.junction.feldberger.mgmt.presentation.components.jfx;

public sealed interface ApplicationNavRoute {

    record RegistrationForm(String username, String password, String repeatPassword) implements ApplicationNavRoute {

        public RegistrationForm(String username) {

            this(username, "", "");
        }
    }

    record LoginForm(String username, String password) implements ApplicationNavRoute {

        public LoginForm(String username) {

            this(username, "");
        }

    }

    record UserSession(int userID, String username) implements ApplicationNavRoute {
    }
}
