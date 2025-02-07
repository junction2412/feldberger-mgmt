package de.code.junction.feldberger.mgmt.presentation.messaging;

import java.util.function.Consumer;

/**
 * A Messenger is used to prompt the user for input.
 */
public interface Messenger {

    /**
     * Send a message.
     *
     * @param message    message details to be displayed
     * @param onResponse a callback to be performed after the user responded
     */
    void send(Message message, Consumer<MessageResponse> onResponse);

    default void send(Message message, Runnable doAfter) {
        send(message, _ -> doAfter.run());
    }

    default void send(Message message) {
        send(message, () -> { /* NOOP */ });
    }
}
