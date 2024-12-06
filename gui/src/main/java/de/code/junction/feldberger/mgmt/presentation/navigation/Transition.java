package de.code.junction.feldberger.mgmt.presentation.navigation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

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

    /**
     * A convenience method of instantiating the {@link Transition}.
     * Therefore, a single parameter lambda can be passed as well.
     *
     * @param transition consumer
     * @param <T>        data the transition is performed upon
     * @return transition orchestrator
     */
    public static <T> Transition<T, T> immediate(Consumer<T> transition) {

        return new Transition<>(TransitionLifecycle.immediate(transition));
    }

    public static <A, B> Transition<A, B> bypass(Function<A, B> converter,
                                                 Consumer<B> transition) {

        return new Transition<>(TransitionLifecycle.bypass(
                converter,
                transition
        ));
    }
}
