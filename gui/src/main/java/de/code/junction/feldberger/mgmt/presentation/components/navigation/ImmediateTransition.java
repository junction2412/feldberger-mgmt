package de.code.junction.feldberger.mgmt.presentation.components.navigation;

/**
 * An ImmediateTransition skips the first two parts of the transition lifecycle.
 *
 * @param <T> data the transition is performed upon
 * @author J. Murray
 */
@FunctionalInterface
public interface ImmediateTransition<T> extends Transition<T, T> {

    @Override
    default void start(T t) {
    }

    @Override
    default T convert(T t) {
        return t;
    }
}
