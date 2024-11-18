package de.code.junction.feldberger.mgmt.presentation.components.navigation;

/**
 * An ImmediateTransition skips the first two parts of the transition lifecycle.
 *
 * @param <T> data the transition is performed upon
 * @author J. Murray
 */
@FunctionalInterface
public interface ImmediateTransition<T> extends BypassTransition<T, T> {

    @Override
    default T convert(T t) {
        return t;
    }
}
