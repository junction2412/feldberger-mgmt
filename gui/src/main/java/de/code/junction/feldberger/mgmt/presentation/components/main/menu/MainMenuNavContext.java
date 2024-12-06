package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.function.Function;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavRoute.*;
import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavRoute.CustomerEditor.BackAction;

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
            case CustomerOverview navRoute -> customerOverview(navRoute.customerId());
            case CustomerEditor navRoute -> customerEditor(navRoute.customer(), navRoute.backAction());
            case CustomerDashboard navRoute -> customerDashboard(navRoute.customer());
        };

        setChildController(controller);
    }

    private FXController customerOverview(int customerId) {

        final var viewCustomerTransition = Transition.<Customer>immediate(customer -> {
            final CustomerDashboard navRoute = new CustomerDashboard(customer);
            navigateTo(navRoute);
        });

        final Function<Customer, CustomerEditor> editor = customer -> new CustomerEditor(
                customer,
                BackAction.OVERVIEW
        );

        final var editCustomerTransition = Transition.<Customer, CustomerEditor>bypass(
                customer -> new CustomerEditor(customer, BackAction.OVERVIEW),
                this::navigateTo
        );

        final var newCustomerTransition = Transition.<Void, CustomerEditor>bypass(
                _ -> editor.apply(new Customer()),
                this::navigateTo
        );

        return controllerFactory.customerOverview(
                viewCustomerTransition,
                editCustomerTransition,
                newCustomerTransition,
                customerId
        );
    }

    private FXController customerEditor(Customer customer,
                                        BackAction backAction) {

        final Transition<Customer, ?> backTransition = (backAction == BackAction.OVERVIEW) ? Transition.bypass(
                CustomerOverview::new,
                this::navigateTo
        ) : Transition.bypass(
                CustomerDashboard::new,
                this::navigateTo
        );

        final var saveTransition = transitionFactory.customerEditorCustomerDashboard(this::navigateTo);

        return controllerFactory.customerEditor(
                customer,
                backTransition,
                saveTransition
        );
    }

    private FXController customerDashboard(Customer customer) {

        final var backTransition = Transition.<Customer, CustomerOverview>bypass(
                CustomerOverview::new,
                this::navigateTo
        );

        final var editCustomerTransition = Transition.<Customer, CustomerEditor>bypass(
                _customer -> new CustomerEditor(
                        _customer,
                        BackAction.DASHBOARD
                ),
                this::navigateTo
        );

        final var newTransactionTransition = Transition.<Customer>immediate(_ -> System.out.println("NOOP"));

        return controllerFactory.customerDashboard(
                customer,
                backTransition,
                editCustomerTransition,
                newTransactionTransition
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
