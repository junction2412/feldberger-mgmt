package de.code.junction.feldberger.mgmt.presentation.navigation;

import java.util.concurrent.CompletableFuture;

/**
 * The preferred approach of performing UI transitions.
 *
 * @param <A> transition input type
 * @param <B> data the transition is performed upon
 * @author J. Murray
 * @see TransitionLifecycle
 */
public class Transition<A, B> {

    private final TransitionLifecycle<A, B> transitionLifecycle;

    public Transition(TransitionLifecycle<A, B> transitionLifecycle) {

        this.transitionLifecycle = transitionLifecycle;
    }

    /**
     * Asynchronously orchestrates the pre-defined UI transition.
     *
     * @param a transition input data
     */
    public void orchestrate(A a) {

        CompletableFuture.supplyAsync(() -> transitionLifecycle.validate(a))
                .thenAcceptAsync(isValid -> {
                    if (!isValid) return;

                    final B b = transitionLifecycle.transform(a);
                    transitionLifecycle.conclude(b);
                }).exceptionally(e -> {
                    e.printStackTrace(System.err);
                    return null;
                });
    }
}
