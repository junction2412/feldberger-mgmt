package de.code.junction.feldberger.mgmt.presentation.components.navigation;

/**
 * The Transition defines the transition lifecycle of UI view components.
 *
 * @param <A> transition input type
 * @param <B> the type the transition is performed upon
 * @author J. Murray
 */
public interface Transition<A, B> {

    /**
     * Start the transition, You can validate the input here if necessary.
     *
     * @param a transition input
     */
    void start(A a);

    /**
     * Convert the transition input.
     *
     * @param a transition input
     * @return data the transition is performed upon
     */
    B convert(A a);

    /**
     * Perform the actual UI transition with the necessary data.
     *
     * @param b data the transition is performed upon
     */
    void end(B b);
}
