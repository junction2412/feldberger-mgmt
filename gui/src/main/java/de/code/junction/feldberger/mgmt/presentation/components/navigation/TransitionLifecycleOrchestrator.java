package de.code.junction.feldberger.mgmt.presentation.components.navigation;

/**
 * The TransitionLifecycleOrchestrator is the preferred approach of performing UI transitions.
 *
 * @param <A> transition input type
 * @param <B> data the transition is performed upon
 * @author J. Murray
 */
public class TransitionLifecycleOrchestrator<A, B> {

    private final Transition<A, B> transition;

    public TransitionLifecycleOrchestrator(Transition<A, B> transition) {

        this.transition = transition;
    }

    /**
     * Orchestrates the pre-defined UI transition.
     *
     * @param a transition input data
     */
    public void orchestrate(A a) {

        transition.start(a);
        final B b = transition.convert(a);
        transition.end(b);
    }

    /**
     * A convenience method of instantiating the {@link TransitionLifecycleOrchestrator} with
     * an {@link ImmediateTransition}. Therefore, a single parameter lambda can be passed as well.
     *
     * @param transition immediate transition or lambda
     * @param <T>        data the transition is performed upon
     * @return transition lifecycle orchestrator
     */
    public static <T> TransitionLifecycleOrchestrator<T, T> immediate(ImmediateTransition<T> transition) {

        return new TransitionLifecycleOrchestrator<>(transition);
    }
}
