package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.presentation.messaging.Message;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;

public sealed interface CustomerSaveResult {

    record Created(int id) implements CustomerSaveResult {
    }

    Updated UPDATED = new Updated();

    record Updated() implements CustomerSaveResult {
    }

    enum Failure implements CustomerSaveResult {
        IDNO_CONSTRAINT_VIOLATION(Messages.CUSTOMER_EDITOR_FAILED_IDNO_CONSTRAINT_VIOLATION),
        ;

        private final Message message;

        Failure(Message message) {
            this.message = message;
        }

        public Message message() {
            return message;
        }
    }
}
