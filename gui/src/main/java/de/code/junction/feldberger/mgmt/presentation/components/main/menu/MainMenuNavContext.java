package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.presentation.navigation.NavContext;
import javafx.scene.Parent;

public class MainMenuNavContext implements NavContext<MainMenuNavRoute> {


    private final MainMenuTransitionFactory transitionFactory;
    private final MainMenuControllerFactory controllerFactory;

    private Parent parent;

    public MainMenuNavContext(MainMenuTransitionFactory transitionFactory, MainMenuControllerFactory controllerFactory) {

        this.transitionFactory = transitionFactory;
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void navigateTo(MainMenuNavRoute route) {

        if (parent == null)
            throw new NullPointerException("Cannot navigate if parent is null.");

        switch (route.destination()) {

            case Subview subview -> {
                switch (subview) {
                    case CUSTOMERS -> controllerFactory.customers(route.userID());
                    default -> {
                    }
                }
            }
        }
    }

    /**
     * Use to inject a parent the navContext takes control of.
     * Setting a parent won't trigger a UI transition.
     *
     * @param parent controlled parent
     */
    public void setParent(Parent parent) {

        this.parent = parent;
    }
}
