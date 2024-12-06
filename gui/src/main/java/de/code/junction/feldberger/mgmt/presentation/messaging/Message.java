package de.code.junction.feldberger.mgmt.presentation.messaging;

import java.util.function.Consumer;

/**
 * A Message contains details to be displayed in the ui
 *
 * @param type       message type
 * @param title      title
 * @param header     concise eye-catching header text
 * @param content    the actual message
 * @param onResponse optional callback to a user response
 * @author J. Murray
 */
public record Message(
        MessageType type,
        String title,
        String header,
        String content,
        Consumer<MessageResponse> onResponse
) {

    public Message(MessageType type,
                   String title,
                   String header,
                   String content) {

        this(
                type,
                title,
                header,
                content,
                _ -> { /* NOOP */ }
        );
    }
}
