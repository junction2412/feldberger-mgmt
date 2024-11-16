package de.code.junction.feldberger.mgmt.presentation.components.messaging;

import java.util.function.Consumer;

public record Message(
        MessageType type,
        String title,
        String header,
        String content,
        Consumer<MessageResponse> onResponse
) {
}
