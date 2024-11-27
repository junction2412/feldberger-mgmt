package de.code.junction.feldberger.mgmt.presentation.navigation;

public abstract class ScopedNavContext<S, R> implements NavContext<R> {

    /**
     * Use to inject a scope the navContext takes control of.
     * Setting a scope won't trigger a UI transition.
     */
    protected S scope;

    public S getScope() {

        return scope;
    }

    public void setScope(S scope) {

        this.scope = scope;
    }
}
