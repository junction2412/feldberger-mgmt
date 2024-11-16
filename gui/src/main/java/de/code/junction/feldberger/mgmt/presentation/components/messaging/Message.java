package de.code.junction.feldberger.mgmt.presentation.components.messaging;

import java.util.function.Consumer;

public record Message(
        MessageType type,
        String title,
        String header,
        String content,
        Consumer<MessageResponse> onResponse
) {

    public Message(MessageType type, String title, String header, String content) {

        this(type, title, header, content, _ -> { /* NOOP */ });
    }
}
