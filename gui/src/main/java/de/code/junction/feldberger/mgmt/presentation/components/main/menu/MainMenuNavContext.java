package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.RouteStack;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Stack;

public class MainMenuNavContext extends RouteStack<Pane, MainMenuRoute> {

    private final MainMenuTransitionFactory transitionFactory;
    private final MainMenuControllerFactory controllerFactory;

    public MainMenuNavContext(Stack<Route<MainMenuRoute>> stack,
                              MainMenuTransitionFactory transitionFactory,
                              MainMenuControllerFactory controllerFactory) {

        super(stack);

        this.transitionFactory = transitionFactory;
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void navigateTo(Route<MainMenuRoute> route) {

        if (scope == null)
            throw new NullPointerException("Cannot navigate if parent is null.");

        final var controller = switch (route.name()) {
            case CUSTOMER_OVERVIEW -> customerOverview(route.cache());
            case CUSTOMER_EDITOR -> customerEditor(route.cache());
            case CUSTOMER_DASHBOARD -> customerDashboard(route.cache());
        };

        setChildController(controller);
    }

    private FXController customerOverview(HashMap<String, Object> cache) {

        final var viewCustomerTransition = Transition.<Customer, Route<MainMenuRoute>>bypass(
                customer -> {
                    final var _cache = new HashMap<String, Object>();
                    _cache.put("customerId", customer.getId());

                    return new Route<>(MainMenuRoute.CUSTOMER_DASHBOARD, _cache);
                },
                this::push
        );

        final var editCustomerTransition = Transition.<Customer, Route<MainMenuRoute>>bypass(
                customer -> {
                    final var _cache = new HashMap<String, Object>();
                    _cache.put("customerId", customer.getId());

                    return new Route<>(MainMenuRoute.CUSTOMER_EDITOR, _cache);
                },
                this::push
        );

        final var newCustomerTransition = Transition.<Void, Route<MainMenuRoute>>bypass(
                _ -> {
                    final var _cache = new HashMap<String, Object>();
                    _cache.put("customerId", 0);

                    return new Route<>(MainMenuRoute.CUSTOMER_EDITOR, _cache);
                },
                this::push
        );

        return controllerFactory.customerOverview(
                viewCustomerTransition,
                editCustomerTransition,
                newCustomerTransition,
                cache
        );
    }

    private FXController customerEditor(HashMap<String, Object> cache) {

        final var backTransition = Transition.<Customer>immediate(_ -> pop());

        final var isPop = (boolean) cache.getOrDefault("pop", false);
        final var saveTransition = transitionFactory.customerEditorCustomerDashboard(route -> {
            if (isPop)
                pop();

            else swap(route);
        });

        return controllerFactory.customerEditor(
                backTransition,
                saveTransition,
                cache
        );
    }

    private FXController customerDashboard(HashMap<String, Object> cache) {

        final var backTransition = Transition.<Void>immediate(_ -> pop());

        final var editCustomerTransition = Transition.<Integer, Route<MainMenuRoute>>bypass(customerId -> {
                    final var _cache = new HashMap<String, Object>();
                    _cache.put("customerId", customerId);
                    _cache.put("pop", true);

                    return new Route<>(MainMenuRoute.CUSTOMER_EDITOR, _cache);
                },
                this::push
        );

        final var newTransactionTransition = Transition.<Void>immediate(_ -> System.out.println("NOOP"));

        return controllerFactory.customerDashboard(
                backTransition,
                editCustomerTransition,
                newTransactionTransition,
                cache
        );
    }

    private void setChildController(FXController controller) {

        final Runnable runnable = () -> {
            final var node = controller == null
                    ? new Pane()
                    : controller.load();

            scope.getChildren().setAll(node);
        };

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }
}
