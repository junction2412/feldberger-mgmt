package de.code.junction.feldberger.mgmt.presentation.navigation;

import java.util.Stack;

public abstract class RouteStack<N, S extends RouteName> extends ScopedNavContext<N, Route<S>> {

    private final Stack<Route<S>> stack;

    protected RouteStack(Stack<Route<S>> stack) {

        this.stack = stack;
    }

    public void push(Route<S> route) {

        stack.push(route);
        navigateTo(route);
    }

    public void pop() {

        stack.pop();
        final var route = (!stack.isEmpty())
                ? stack.peek()
                : null;

        if (route != null)
            navigateTo(route);
    }

    public Route<S> peek() {

        return stack.peek();
    }

    @Override
    public void setScope(N scope) {

        super.setScope(scope);

        if (!stack.isEmpty())
            navigateTo(peek());
    }
}
