package de.code.junction.feldberger.mgmt.presentation.components.navigation;

@FunctionalInterface
public interface TransitionManager<T> {

    void transition(T t);
}
