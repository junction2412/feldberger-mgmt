package de.code.junction.feldberger.mgmt.presentation.navigation;

/**
 * A simple mediator intended to be used as a navigator throughout a given context.
 *
 * @param <R> navigation route
 * @author J. Murray
 */
public interface NavContext<R> {

    /**
     * Navigate via a given route.
     *
     * @param route route
     */
    void navigateTo(R route);
}
