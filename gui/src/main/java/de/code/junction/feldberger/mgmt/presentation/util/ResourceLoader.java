package de.code.junction.feldberger.mgmt.presentation.util;

import de.code.junction.feldberger.mgmt.presentation.messaging.Message;

import java.util.ResourceBundle;

/**
 * ResourceLoader is a utility class to access specific application resources.
 */
public final class ResourceLoader {

    private ResourceLoader() {
    }

    /**
     * Retrieve the resource bundle with keys for labels.
     *
     * @return corresponding resource bundle
     */
    public static ResourceBundle getLabelStringResources() {

        return ResourceBundle.getBundle("de/code/junction/feldberger/mgmt/i18n/labels");
    }

    /**
     * Retrieve the resource bundle with keys for {@link Message}s.
     *
     * @return corresponding resource bundle
     */
    public static ResourceBundle getMessageStringResources() {

        return ResourceBundle.getBundle("de/code/junction/feldberger/mgmt/i18n/messages");
    }
}
