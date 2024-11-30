package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.navigation.ScopedNavContext;
import de.code.junction.feldberger.mgmt.presentation.navigation.Transition;
import de.code.junction.feldberger.mgmt.presentation.navigation.TransitionLifecycle;
import de.code.junction.feldberger.mgmt.presentation.view.FXController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuNavRoute.*;

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

        final Transition<Customer, Customer> viewCustomerTransition = Transition.immediate(
                _ -> System.out.println("NOOP"));

        final Transition<Customer, Customer> editCustomerTransition = Transition.immediate(
                _ -> System.out.println("NOOP"));

        final TransitionLifecycle<Void, Customer> bypass = TransitionLifecycle.bypass(
                _ -> new Customer(),
                customer -> navigateTo(new CustomerEditor(customer))
        );

        final Transition<Void, Customer> newCustomerTransition = new Transition<>(bypass);

        return controllerFactory.customerOverview(viewCustomerTransition, editCustomerTransition, newCustomerTransition);
    }

    private FXController customerEditor(CustomerEditor route) {

        final Transition<Customer, Customer> backTransition = Transition.immediate(
                _ -> navigateTo(Subview.CUSTOMERS));

        final Transition<Customer, Customer> saveTransition = transitionFactory.customerEditorCustomerDashboard(
                customer -> navigateTo(new CustomerDashboard(customer)));

        return controllerFactory.customerEditor(route.customer(), backTransition, saveTransition);
    }

    private FXController customerDashboard(CustomerDashboard route) {

        final Transition<Customer, Customer> backTransition = Transition.immediate(_ -> navigateTo(Subview.CUSTOMERS));
        final Transition<Customer, Customer> editCustomerTransition = Transition.immediate(customer -> navigateTo(new CustomerEditor(customer)));
        final Transition<Customer, Customer> newTransactionTransition = Transition.immediate(_ -> System.out.println("NOOP"));

        return controllerFactory.customerDashboard(
                route.customer(),
                backTransition,
                editCustomerTransition,
                newTransactionTransition
        );
    }

    private void setChildController(FXController controller) {

        final Runnable runnable = () -> {
            final Node node = controller == null
                    ? null
                    : controller.load();

            scope.getChildren().setAll(node);
        };

        if (Platform.isFxApplicationThread())
            runnable.run();
        else
            Platform.runLater(runnable);
    }
}
