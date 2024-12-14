package de.code.junction.feldberger.mgmt.presentation.messaging;

/**
 * A Messenger is used to prompt the user for input.
 */
@FunctionalInterface
public interface Messenger {

    /**
     * Send a message.
     *
     * @param message message details to be displayed
     */
    void send(Message message);
}
