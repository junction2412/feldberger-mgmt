package de.code.junction.feldberger.mgmt.presentation.components.navigation;

/**
 * A {@link Transition} to always bypass validation.
 *
 * @param <A>
 * @param <B>
 */
public interface BypassTransition<A, B> extends Transition<A, B> {

    @Override
    default boolean validate(A a) {

        return true;
    }
}
