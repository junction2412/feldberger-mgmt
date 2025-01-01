package de.code.junction.feldberger.mgmt.presentation.components.application;

import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.presentation.navigation.RouteName;

public sealed interface ApplicationNavRoute extends RouteName {

    record Login(String username) implements ApplicationNavRoute {

        public Login() {

            this("");
        }

        @Override
        public String string() {

            return "login";
        }
    }

    record Registration(String username) implements ApplicationNavRoute {

        @Override
        public String string() {

            return "registration";
        }
    }

    record MainMenu(User user) implements ApplicationNavRoute {

        @Override
        public String string() {
            return "main-menu";
        }
    }
}
