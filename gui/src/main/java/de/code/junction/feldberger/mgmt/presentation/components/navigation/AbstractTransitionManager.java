package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;

import java.util.function.Consumer;

public abstract class AbstractTransitionManager<T> implements TransitionManager<T> {

    protected final Messenger messenger;
    protected final Consumer<T> onTransition;

    public AbstractTransitionManager(Messenger messenger, Consumer<T> onTransition) {

        this.messenger = messenger;
        this.onTransition = onTransition;
    }
}
