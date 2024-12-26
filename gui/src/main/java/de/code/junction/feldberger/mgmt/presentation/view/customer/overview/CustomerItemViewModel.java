package de.code.junction.feldberger.mgmt.presentation.view.customer.overview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerItemViewModel {

    private final IntegerProperty id;
    private final StringProperty idNo;
    private final StringProperty nameOrCompanyName;

    public CustomerItemViewModel(int id, String idNo, String nameOrCompanyName) {

        this.id = new SimpleIntegerProperty(this, "id", id);
        this.idNo = new SimpleStringProperty(this, "idNo", idNo);
        this.nameOrCompanyName = new SimpleStringProperty(this, "nameOrCompanyName", nameOrCompanyName);
    }

    public int getId() {

        return id.get();
    }

    public IntegerProperty idProperty() {

        return id;
    }

    public void setId(int id) {

        this.id.set(id);
    }

    public String getIdNo() {

        return idNo.get();
    }

    public StringProperty idNoProperty() {

        return idNo;
    }

    public void setIdNo(String idNo) {

        this.idNo.set(idNo);
    }

    public String getNameOrCompanyName() {

        return nameOrCompanyName.get();
    }

    public StringProperty nameOrCompanyNameProperty() {

        return nameOrCompanyName;
    }

    public void setNameOrCompanyName(String nameOrCompanyName) {

        this.nameOrCompanyName.set(nameOrCompanyName);
    }
}
