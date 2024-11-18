package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Transition defines the transition lifecycle of UI view components.
 * As such it must not make use of components that may only exist in the UI layer, as it's intended to be orchestrated
 * asynchronously.
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
     * Perform the actual UI transition with the necessary data.
     *
     * @param b data the transition is performed upon
     */
    void conclude(B b);

    /**
     * A factory method to instantiate a fully fledged transition on a whim.
     *
     * @param validator   validator
     * @param transformer transformer
     * @param consumer    consumer
     * @param <A>         transition input
     * @param <B>         data the transition is performed upon
     * @return transition
     */
    static <A, B> Transition<A, B> of(Predicate<A> validator, Function<A, B> transformer, Consumer<B> consumer) {

        return new Transition<>() {

            @Override
            public boolean validate(A a) {

                return validator.test(a);
            }

            @Override
            public B transform(A a) {

                return transformer.apply(a);
            }

            @Override
            public void conclude(B b) {

                consumer.accept(b);
            }
        };
    }

    /**
     * A factory method to instantiate a transition without input validation.
     *
     * @param transformer transformer
     * @param consumer    consumer
     * @param <A>         transition input
     * @param <B>         data the transition is performed upon
     * @return transition without input validation
     */
    static <A, B> Transition<A, B> bypass(Function<A, B> transformer, Consumer<B> consumer) {

        return of(_ -> true, transformer, consumer);
    }

    /**
     * A factory method to instantiate a transition without input validation and type conversion.
     *
     * @param consumer consumer
     * @param <T>      data the transition is performed upon
     * @return transition without input validation and type conversion
     */
    static <T> Transition<T, T> immediate(Consumer<T> consumer) {

        return bypass(t -> t, consumer);
    }
}
