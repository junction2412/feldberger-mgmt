package de.code.junction.feldberger.mgmt.presentation.components.navigation;

/**
 * The TransitionManager acts as a gate between views.
 * It can be used to validate transition parameters and perform the transition after successful validation.
 * Validation can also be omitted. Therefore, it acts as a unidirectional data flow.
 *
 * @param <T> Expected transition parameter
 * @author J. Murray
 */
@FunctionalInterface
public interface TransitionManager<T> {

    /**
     * Perform the transition to another specified view.
     *
     * @param t transition parameter
     */
    void transition(T t);
}
