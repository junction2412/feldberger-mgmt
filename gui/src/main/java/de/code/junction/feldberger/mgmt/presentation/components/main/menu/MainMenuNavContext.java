package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.function.Consumer;

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
            case Subview subview -> switch (subview) {
                case CUSTOMERS -> customerOverview();
                default -> null;
            };

            case CustomerEditor editorRoute -> customerEditor(editorRoute);
            case CustomerDashboard dashboardRoute -> customerDashboard(dashboardRoute);
        };

        setChildController(controller);
    }

    private FXController customerOverview() {

        final var viewCustomerTransition = Transition.<Customer>immediate(customer -> {
            final CustomerDashboard navRoute = new CustomerDashboard(customer);
            navigateTo(navRoute);
        });

        final Consumer<Customer> editorConsumer = customer -> {
            final CustomerEditor navRoute = new CustomerEditor(customer, BackAction.OVERVIEW);
            navigateTo(navRoute);
        };

        final var editCustomerTransition = Transition.immediate(editorConsumer);
        final var bypass = TransitionLifecycle.<Void, Customer>bypass(_ -> new Customer(),
                editorConsumer);

        final var newCustomerTransition = new Transition<>(bypass);

        return controllerFactory.customerOverview(
                viewCustomerTransition,
                editCustomerTransition,
                newCustomerTransition
        );
    }

    private FXController customerEditor(CustomerEditor route) {

        final Transition<Customer, Customer> backTransition = route.backAction() == BackAction.OVERVIEW
                ? Transition.immediate(_ -> navigateTo(Subview.CUSTOMERS))
                : Transition.immediate(customer -> navigateTo(new CustomerDashboard(customer)));

        final Transition<Customer, Customer> saveTransition = transitionFactory.customerEditorCustomerDashboard(
                customer -> navigateTo(new CustomerDashboard(customer)));

        return controllerFactory.customerEditor(route.customer(), backTransition, saveTransition);
    }

    private FXController customerDashboard(CustomerDashboard route) {

        final var backTransition = Transition.<Customer>immediate(_ -> navigateTo(Subview.CUSTOMERS));

        final var editCustomerTransition = Transition.<Customer>immediate(customer -> {
            final CustomerEditor navRoute = new CustomerEditor(customer, BackAction.DASHBOARD);
            navigateTo(navRoute);
        });

        final var newTransactionTransition = Transition.<Customer>immediate(_ -> System.out.println("NOOP"));

        return controllerFactory.customerDashboard(
                route.customer(),
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
