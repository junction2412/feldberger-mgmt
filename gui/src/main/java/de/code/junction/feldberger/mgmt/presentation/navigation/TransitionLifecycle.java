package de.code.junction.feldberger.mgmt.presentation.navigation;

/**
 * Defines the transition lifecycle of UI view components.
 * As such it must not be invoked manually, as it's intended to be orchestrated asynchronously.
 *
 * @param <A> transition input type
 * @param <B> the type the transition is performed upon
 * @author J. Murray
 */
public interface TransitionLifecycle<A, B> {

    /**
     * Start the transition, You can validate the input here if necessary.
     *
     * @param a transition input
     * @return whether the input is valid
     */
    boolean validate(A a);

    /**
     * Convert the transition input.
     *
     * @param a transition input
     * @return data the transition is performed upon
     */
    B transform(A a);

    /**
     * Conclude the actual UI transition.
     *
     * @param b data the transition is performed upon
     */
    void conclude(B b);
}
