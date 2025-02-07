package de.code.junction.feldberger.mgmt.presentation.messaging;

import static de.code.junction.feldberger.mgmt.presentation.util.ResourceLoader.getMessageStringResources;

/**
 * A collection of constant messages.
 *
 * @author J. Murray
 */
public final class Messages {

    private Messages() {
    }

    public static final Message TRANSITION_NOT_PERFORMED;

    public static final Message REGISTRATION_SUCCEEDED_USER_INACTIVE;

    public static final Message LOGIN_FAILED_WRONG_CREDENTIALS;

    public static final Message CUSTOMER_EDITOR_CONFIRM_UNSAVED_CHANGES;
    public static final Message CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION;

    static {

        final var bundle = getMessageStringResources();

        TRANSITION_NOT_PERFORMED = new Message(
                MessageType.ERROR,
                bundle.getString("transition.error.not.performed.title"),
                bundle.getString("transition.error.not.performed.header"),
                bundle.getString("transition.error.not.performed.content")
        );

        REGISTRATION_SUCCEEDED_USER_INACTIVE = new Message(
                MessageType.INFORMATION,
                bundle.getString("registration.succeeded.user.inactive.title"),
                bundle.getString("registration.succeeded.user.inactive.header"),
                bundle.getString("registration.succeeded.user.inactive.content")
        );

        LOGIN_FAILED_WRONG_CREDENTIALS = new Message(
                MessageType.WARNING,
                bundle.getString("login.failed.wrong.credentials.title"),
                bundle.getString("login.failed.wrong.credentials.header"),
                bundle.getString("login.failed.wrong.credentials.content")
        );

        CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION = new Message(
                MessageType.WARNING,
                bundle.getString("customer.editor.failed.idno.constraint.violation.title"),
                bundle.getString("customer.editor.failed.idno.constraint.violation.header"),
                bundle.getString("customer.editor.failed.idno.constraint.violation.content")
        );

        CUSTOMER_EDITOR_CONFIRM_UNSAVED_CHANGES = new Message(
                MessageType.CONFIRMATION,
                bundle.getString("customer.editor.confirmation.unsaved.changes.title"),
                bundle.getString("customer.editor.confirmation.unsaved.changes.header"),
                bundle.getString("customer.editor.confirmation.unsaved.changes.content")
        );
    }
}
