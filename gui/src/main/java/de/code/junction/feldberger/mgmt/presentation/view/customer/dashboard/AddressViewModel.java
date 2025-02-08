package de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddressViewModel {

    private final IntegerProperty id;
    private final StringProperty countryCode;
    private final StringProperty postalCode;
    private final StringProperty city;
    private final StringProperty street;
    private final StringProperty streetNumber;
    private final StringProperty suffix;

    public AddressViewModel(int id,
                            String countryCode,
                            String postalCode,
                            String city,
                            String street,
                            String streetNumber,
                            String suffix) {

        this.id = new SimpleIntegerProperty(id);
        this.countryCode = new SimpleStringProperty(countryCode);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.streetNumber = new SimpleStringProperty(streetNumber);
        this.suffix = new SimpleStringProperty(suffix);
    }

    public AddressViewModel(Address address) {

        this(
                address.getId(),
                address.getCountryCode(),
                address.getPostalCode(),
                address.getCity(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getSuffix()
        );
    }

    public AddressViewModel() {

        this(
                0,
                "",
                "",
                "",
                "",
                "",
                ""
        );
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

    public String getCountryCode() {

        return countryCode.get();
    }

    public StringProperty countryCodeProperty() {

        return countryCode;
    }

    public void setCountryCode(String countryCode) {

        this.countryCode.set(countryCode);
    }

    public String getPostalCode() {

        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {

        return postalCode;
    }

    public void setPostalCode(String postalCode) {

        this.postalCode.set(postalCode);
    }

    public String getCity() {

        return city.get();
    }

    public StringProperty cityProperty() {

        return city;
    }

    public void setCity(String city) {

        this.city.set(city);
    }

    public String getStreet() {

        return street.get();
    }

    public StringProperty streetProperty() {

        return street;
    }

    public void setStreet(String street) {

        this.street.set(street);
    }

    public String getStreetNumber() {

        return streetNumber.get();
    }

    public StringProperty streetNumberProperty() {

        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {

        this.streetNumber.set(streetNumber);
    }

    public String getSuffix() {

        return suffix.get();
    }

    public StringProperty suffixProperty() {

        return suffix;
    }

    public void setSuffix(String suffix) {

        this.suffix.set(suffix);
    }

    public Address toAddress() {

        return new Address(
                getId(),
                getCountryCode(),
                getPostalCode(),
                getCity(),
                getStreet(),
                getStreetNumber(),
                getSuffix()
        );
    }
}
