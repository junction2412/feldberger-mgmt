package de.code.junction.feldberger.mgmt.presentation.components.main.menu;

public enum Subview {
    NONE(null),
    CUSTOMERS("enum.main.menu.nav.route.customers"),
    ORDERS("enum.main.menu.nav.route.orders"),
    INVOICES("enum.main.menu.nav.route.invoices"),
    PACKING_SLIPS("enum.main.menu.nav.route.packing_slips"),
    ;

    private final String labelKey;

    Subview(String labelKey) {

        this.labelKey = labelKey;
    }

    public String getLabelKey() {

        return labelKey;
    }
}
