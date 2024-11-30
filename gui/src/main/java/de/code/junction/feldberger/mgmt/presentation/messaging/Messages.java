package de.code.junction.feldberger.mgmt.presentation.messaging;

import java.util.ResourceBundle;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceUtil.getMessageStringResources;

/**
 * A collection of static messages.
 *
 * @author J. Murray
 */
public final class Messages {

    private Messages() {
    }

    public static final Message TRANSITION_NOT_PERFORMED;

    public static final Message LOGIN_FAILED_WRONG_CREDENTIALS;
    public static final Message CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION;

    static {

        final ResourceBundle bundle = getMessageStringResources();

        TRANSITION_NOT_PERFORMED = new Message(
                MessageType.ERROR,
                bundle.getString("transition.error.not.performed.title"),
                bundle.getString("transition.error.not.performed.header"),
                bundle.getString("transition.error.not.performed.content")
        );

        LOGIN_FAILED_WRONG_CREDENTIALS = new Message(
                MessageType.WARNING,
                bundle.getString("login.failed.wrong.credentials.title"),
                bundle.getString("login.failed.wrong.credentials.header"),
                bundle.getString("login.failed.wrong.credentials.content")
        );

        CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION = new Message(
                MessageType.WARNING,
                bundle.getString("customer_editor.failed.idno.constraint.violation.title"),
                bundle.getString("customer_editor.failed.idno.constraint.violation.header"),
                bundle.getString("customer_editor.failed.idno.constraint.violation.content")
        );
    }
}
