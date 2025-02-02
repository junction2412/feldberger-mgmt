package de.code.junction.feldberger.mgmt.presentation.components.application;

public sealed interface ApplicationRoute {

    record Login(String username) implements ApplicationRoute {

        public Login() {

            this("");
        }
    }

    record Registration(String username) implements ApplicationRoute {

        public Registration() {

            this("");
        }
    }

    record MainMenu(int userId, String username) implements ApplicationRoute {
    }
}
