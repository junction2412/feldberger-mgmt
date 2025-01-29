package de.code.junction.feldberger.mgmt.presentation.navigation;

/// Use to inject a scope the [Navigator] takes control of.
/// Setting a scope won't trigger a UI transition.
///
/// @author J. Murray
public abstract class ScopedNavigator<S, R> implements Navigator<R> {

    protected S scope;

    @SuppressWarnings("unused")
    public S getScope() {

        return scope;
    }

    public void setScope(S scope) {

        this.scope = scope;
    }
}
