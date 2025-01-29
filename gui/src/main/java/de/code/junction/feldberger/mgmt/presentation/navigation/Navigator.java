package de.code.junction.feldberger.mgmt.presentation.navigation;

/// A simple mediator intended to be used for navigation throughout the app.
///
/// @param <R> navigation route
/// @author J. Murray
public interface Navigator<R> {

    /// Navigate via a given route.
    ///
    /// @param route route
    void navigateTo(R route);
}
