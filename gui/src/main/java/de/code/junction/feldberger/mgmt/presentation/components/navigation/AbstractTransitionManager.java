package de.code.junction.feldberger.mgmt.presentation.components.navigation;

import de.code.junction.feldberger.mgmt.presentation.components.messaging.Messenger;

import java.util.function.Consumer;

/**
 * The AbstractTransitionManager acts as a helper definition for {@link TransitionManager} implementations, that
 * are too complex to keep in a single lambda.
 *
 * @param <T> expected transition parameter
 * @author J. Murray
 */
public abstract class AbstractTransitionManager<T> implements TransitionManager<T> {

    /**
     * A {@link Messenger} to be used to prompt for user input.
     */
    protected final Messenger messenger;

    /**
     * A callback to be performed after successful input validation.
     * This is used to further decouple UI transition logic from what has to be done beforehand.
     */
    protected final Consumer<T> onTransition;

    public AbstractTransitionManager(Messenger messenger, Consumer<T> onTransition) {

        this.messenger = messenger;
        this.onTransition = onTransition;
    }
}
