package de.code.junction.feldberger.mgmt.presentation.components.messaging;

@FunctionalInterface
public interface Messenger {

    void send(Message message);
}
