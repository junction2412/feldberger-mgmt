package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Optional;
import java.util.function.Consumer;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.*;

public class MainMenuNavigator extends ScopedNavigator<Pane, MainMenuRoute> {

    private final MainMenuTransitionFactory transitionFactory;
    private final MainMenuControllerFactory controllerFactory;

    public MainMenuNavigator(MainMenuTransitionFactory transitionFactory, MainMenuControllerFactory controllerFactory) {

        this.transitionFactory = transitionFactory;
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void navigateTo(MainMenuRoute route) {

        if (scope == null)
            throw new NullPointerException("Cannot navigate if scope is null.");

        final var controller = switch (route) {
            case CustomerOverview overview -> customerOverview(overview.selectedCustomerId());
            case CustomerEditor editor -> customerEditor(editor.customerId(), editor.fromDashboard());
            case CustomerDashboard dashboard -> customerDashboard(
                    dashboard.customerId(),
                    dashboard.selectedTransactionId()
            );
        };

        setChildController(controller);
    }

    private FXLoadable customerOverview(int selectedCustomerId) {

        final Consumer<Integer> onViewCustomerClicked = customerId -> navigateTo(new CustomerDashboard(customerId));
        final Consumer<Integer> onEditCustomerClicked = customerId -> navigateTo(new CustomerEditor(customerId));
        final Runnable onNewCustomerClicked = () -> navigateTo(new CustomerEditor());

        return controllerFactory.customerOverview(
                onNewCustomerClicked,
                onEditCustomerClicked,
                onViewCustomerClicked,
                "",
                selectedCustomerId
        );
    }

    private FXLoadable customerEditor(int customerId, boolean fromDashboard) {

        final var NEW_CUSTOMER_ID = 0;
        final var saveTransition = transitionFactory.customerEditorCustomerDashboard(this::navigateTo);

        final var route = (fromDashboard && (customerId != NEW_CUSTOMER_ID))
                ? new CustomerDashboard(customerId)
                : new CustomerOverview();

        final Runnable onBackClicked = () -> navigateTo(route);

        return controllerFactory.customerEditor(onBackClicked, saveTransition::orchestrate, customerId);
    }

    private FXLoadable customerDashboard(int customerId, int selectedTransactionId) {

        final Runnable onBackClicked = () -> navigateTo(new CustomerOverview(customerId));
        final Runnable onEditCustomerClicked = () -> navigateTo(new CustomerEditor(customerId, true));
        final Runnable onNewTransactionClicked = () -> System.out.println("NOOP");

        return controllerFactory.customerDashboard(
                onBackClicked,
                onEditCustomerClicked,
                onNewTransactionClicked,
                customerId,
                selectedTransactionId
        );
    }

    private void setChildController(FXLoadable controller) {

        final Runnable runnable = () -> {

            final var node = Optional.ofNullable(controller)
                    .map(FXLoadable::load)
                    .orElseGet(Pane::new);

            scope.getChildren().setAll(node);

            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
        };

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }
}
