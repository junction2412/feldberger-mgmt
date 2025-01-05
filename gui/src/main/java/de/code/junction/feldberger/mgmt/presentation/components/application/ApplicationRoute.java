package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.user.User;

public sealed interface ApplicationRoute {

    record Login(String username) implements ApplicationRoute {

        public Login() {

            this("");
        }
    }

    record Registration(String username) implements ApplicationRoute {
    }

    record MainMenu(User user) implements ApplicationRoute {
    }
}
