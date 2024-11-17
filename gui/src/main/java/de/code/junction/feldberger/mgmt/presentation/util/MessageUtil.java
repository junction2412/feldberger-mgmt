package de.code.junction.feldberger.mgmt.presentation.util;

import de.code.junction.feldberger.mgmt.presentation.components.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.components.messaging.MessageType;

import java.util.ResourceBundle;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil.getMessageStringResources;

/**
 * Another utility class to assemble specific messages that are used throughout the entire application.
 *
 * @author J. Murray
 */
public final class MessageUtil {

    private MessageUtil() {
    }

    public static Message transitionNotPerformed() {

        final ResourceBundle bundle = getMessageStringResources();

        return new Message(
                MessageType.ERROR,
                bundle.getString("transition.error.not.performed.title"),
                bundle.getString("transition.error.not.performed.header"),
                bundle.getString("transition.error.not.performed.content")
        );
    }
}
