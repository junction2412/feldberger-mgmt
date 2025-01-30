package de.code.junction.feldberger.mgmt.presentation.view;

import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;

public abstract class FXViewModel implements ViewModel {

    private final Messenger messenger;

    public FXViewModel(Messenger messenger) {

        this.messenger = messenger;
    }

    @Override
    public Messenger messenger() {

        return messenger;
    }
}
