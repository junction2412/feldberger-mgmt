package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.presentation.navigation.Route;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.preferences.PreferenceRegistry;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.function.Consumer;

public class MainMenuNavContext extends ScopedNavContext<Pane, Route<MainMenuRoute>> {

    private final MainMenuTransitionFactory transitionFactory;
    private final MainMenuControllerFactory controllerFactory;
    private final PreferenceRegistry preferenceRegistry;

    public MainMenuNavContext(MainMenuTransitionFactory transitionFactory,
                              MainMenuControllerFactory controllerFactory,
                              PreferenceRegistry preferenceRegistry) {

        this.transitionFactory = transitionFactory;
        this.controllerFactory = controllerFactory;
        this.preferenceRegistry = preferenceRegistry;
    }

    @Override
    public void navigateTo(Route<MainMenuRoute> route) {

        if (scope == null)
            throw new NullPointerException("Cannot navigate if scope is null.");

        final var controller = switch (route.name()) {
            case CUSTOMER_OVERVIEW -> customerOverview(route.cache());
            case CUSTOMER_EDITOR -> customerEditor(route.cache());
            case CUSTOMER_DASHBOARD -> customerDashboard(route.cache());
        };

        setChildController(controller);
    }

    private FXController customerOverview(HashMap<String, Object> cache) {

        final Consumer<Integer> onViewCustomerClicked = customerId -> {
            final var _cache = new HashMap<String, Object>();
            _cache.put("customerId", customerId);

            navigateTo(new Route<>(MainMenuRoute.CUSTOMER_DASHBOARD, _cache));
        };

        final Consumer<Integer> onEditCustomerClicked = customerId -> {
            final var _cache = new HashMap<String, Object>();
            _cache.put("customerId", customerId);

            navigateTo(new Route<>(MainMenuRoute.CUSTOMER_EDITOR, _cache));
        };

        final Runnable onNewCustomerClicked = () -> {
            final var _cache = new HashMap<String, Object>();
            _cache.put("customerId", 0);

            navigateTo(new Route<>(MainMenuRoute.CUSTOMER_EDITOR, _cache));
        };


        return controllerFactory.customerOverview(
                onNewCustomerClicked,
                onEditCustomerClicked,
                onViewCustomerClicked,
                cache
        );
    }

    private FXController customerEditor(HashMap<String, Object> cache) {

        final var saveTransition = transitionFactory.customerEditorCustomerDashboard(this::navigateTo);

        final Runnable onBackClicked = () -> {
        };

        return controllerFactory.customerEditor(onBackClicked, saveTransition::orchestrate, cache);
    }

    private FXController customerDashboard(HashMap<String, Object> cache) {

        final Consumer<Integer> onEditCustomerClicked = customerId -> {
            final var _cache = new HashMap<String, Object>();
            _cache.put("customerId", customerId);
            _cache.put("pop", true);

            navigateTo(new Route<>(MainMenuRoute.CUSTOMER_EDITOR, _cache));
        };

        final Consumer<Integer> onNewTransactionClicked = _ -> System.out.println("NOOP");

        final Runnable onBackClicked = () -> {
        };

        return controllerFactory.customerDashboard(
                onBackClicked,
                onEditCustomerClicked,
                onNewTransactionClicked,
                cache
        );
    }

    private void setChildController(FXController controller) {

        final Runnable runnable = () -> {

            final var node = controller == null ? new Pane() : controller.load();
            scope.getChildren().setAll(node);
        };

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }
}
