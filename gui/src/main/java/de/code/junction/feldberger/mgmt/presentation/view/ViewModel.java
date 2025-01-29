package de.code.junction.feldberger.mgmt.presentation.view;

import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;

public interface ViewModel {

    Messenger messenger();

    default void init() {
    }
}
