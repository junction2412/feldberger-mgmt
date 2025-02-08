package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.components.ViewFactory;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXLoadable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Optional;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.*;

public class MainMenuNavigator extends ScopedNavigator<Pane, MainMenuRoute> {

    private final ViewFactory viewFactory;

    public MainMenuNavigator(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    @Override
    public void navigateTo(MainMenuRoute route) {

        if (scope == null)
            throw new NullPointerException("Cannot navigate if scope is null.");

        final var controller = switch (route) {
            case CustomerOverview() -> viewFactory.customerOverview(this);
            case CustomerEditor(MainMenuRoute backRoute, Customer customer) ->
                    viewFactory.customerEditor(this, backRoute, customer);
            case CustomerDashboard(Customer customer) -> viewFactory.customerDashboard(this, customer);
        };

        setChildController(controller);
    }

    private void setChildController(FXLoadable controller) {

        final var node = Optional.ofNullable(controller)
                .map(FXLoadable::load)
                .orElseGet(Pane::new);

        scope.getChildren().setAll(node);

        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
    }
}
