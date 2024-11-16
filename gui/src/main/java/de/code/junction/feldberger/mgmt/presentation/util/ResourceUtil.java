package de.code.junction.feldberger.mgmt.presentation.util;

import java.util.ResourceBundle;

public class ResourceUtil {
    private ResourceUtil(){ }

    public static ResourceBundle getLabelStringResources() {

        return ResourceBundle.getBundle("de/code/junction/feldberger/mgmt/i18n/labels");
    }

    public static ResourceBundle getMessageStringResources() {

        return ResourceBundle.getBundle("de/code/junction/feldberger/mgmt/i18n/messages");
    }
}
