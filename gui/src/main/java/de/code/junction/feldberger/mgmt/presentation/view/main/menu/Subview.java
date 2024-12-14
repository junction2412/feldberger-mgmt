package de.code.junction.feldberger.mgmt.presentation.view.main.menu;

public enum Subview {
    CUSTOMERS("enum.main.menu.nav.route.customers"),
    TRANSACTIONS("enum.main.menu.nav.route.transactions"),
    ;

    private final String labelKey;

    Subview(String labelKey) {

        this.labelKey = labelKey;
    }

    public String getLabelKey() {

        return labelKey;
    }
}
