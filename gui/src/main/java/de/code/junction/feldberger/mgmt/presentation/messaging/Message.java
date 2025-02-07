package de.code.junction.feldberger.mgmt.presentation.messaging;

/**
 * A Message contains details to be displayed in the ui
 *
 * @param type       message type
 * @param title      title
 * @param header     concise eye-catching header text
 * @param content    the actual message
 * @author J. Murray
 */
public record Message(MessageType type, String title, String header, String content) {
}
