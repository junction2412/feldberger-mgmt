package de.code.junction.feldberger.mgmt.presentation.navigation;

public interface PassThroughLifecycle<T> extends TransitionLifecycle<T, T> {

    @Override
    default T transform(T t) {

        return t;
    }
}
