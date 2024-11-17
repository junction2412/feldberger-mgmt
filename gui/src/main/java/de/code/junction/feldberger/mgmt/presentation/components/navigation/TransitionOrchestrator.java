package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import java.util.concurrent.CompletableFuture;

/**
 * The TransitionOrchestrator is the preferred approach of performing UI transitions.
 *
 * @param <A> transition input type
 * @param <B> data the transition is performed upon
 * @author J. Murray
 */
public class TransitionOrchestrator<A, B> {

    private final Transition<A, B> transition;

    public TransitionOrchestrator(Transition<A, B> transition) {

        this.transition = transition;
    }

    /**
     * Asynchronously orchestrates the pre-defined UI transition.
     *
     * @param a transition input data
     */
    public void orchestrate(A a) {

        CompletableFuture.supplyAsync(() -> transition.validate(a))
                .thenAcceptAsync(isValid -> {
                    if (!isValid) return;

                    final B b = transition.convert(a);
                    transition.end(b);
                }).exceptionally(e -> {
                    e.printStackTrace(System.err);
                    return null;
                });
    }

    /**
     * A convenience method of instantiating the {@link TransitionOrchestrator} with
     * an {@link ImmediateTransition}. Therefore, a single parameter lambda can be passed as well.
     *
     * @param transition immediate transition or lambda
     * @param <T>        data the transition is performed upon
     * @return transition lifecycle orchestrator
     */
    public static <T> TransitionOrchestrator<T, T> immediate(ImmediateTransition<T> transition) {

        return new TransitionOrchestrator<>(transition);
    }
}
