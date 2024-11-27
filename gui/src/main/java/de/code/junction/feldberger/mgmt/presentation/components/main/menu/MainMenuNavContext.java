package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavRoute.Subview;

public class MainMenuNavContext extends ScopedNavContext<Pane, MainMenuNavRoute> {

    private final MainMenuTransitionFactory transitionFactory;
    private final MainMenuControllerFactory controllerFactory;
    private final int userID;

    public MainMenuNavContext(MainMenuTransitionFactory transitionFactory,
                              MainMenuControllerFactory controllerFactory,
                              int userID) {

        this.transitionFactory = transitionFactory;
        this.controllerFactory = controllerFactory;
        this.userID = userID;
    }

    @Override
    public void navigateTo(MainMenuNavRoute route) {

        if (scope == null)
            throw new NullPointerException("Cannot navigate if parent is null.");

        final var controller = switch (route) {
            case Subview subview -> switch (subview) {
                case CUSTOMERS -> controllerFactory.customers(userID);
                default -> null;
            };
        };

        setChildController(controller);
    }

    private void setChildController(FXController controller) {

        final Runnable runnable = () -> scope.getChildren().setAll(controller.load());

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }
}
