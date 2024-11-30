package de.code.junction.feldberger.mgmt.presentation.navigation;

public interface PassthroughLifecycle<T> extends TransitionLifecycle<T, T> {

    @Override
    default T transform(T t) {

        return t;
    }
}
