package de.code.junction.feldberger.mgmt.data.access.address;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import de.code.junction.feldberger.mgmt.data.converter.StringColumnEncryptor;
import jakarta.persistence.*;

@Entity(name = "addresses")
@Table(uniqueConstraints = {@UniqueConstraint(name = "U_ADDRESS", columnNames = {
        "country_code", "postal_code", "city", "street", "street_number", "suffix"
})})
public class Address implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country_code", nullable = false)
    @Convert(converter = StringColumnEncryptor.class)
    private String countryCode;

    @Column(name = "postal_code", nullable = false)
    @Convert(converter = StringColumnEncryptor.class)
    private String postalCode;

    @Column(nullable = false)
    @Convert(converter = StringColumnEncryptor.class)
    private String city;

    @Column(nullable = false)
    @Convert(converter = StringColumnEncryptor.class)
    private String street;

    @Column(name = "street_number", nullable = false)
    @Convert(converter = StringColumnEncryptor.class)
    private String streetNumber;

    @Convert(converter = StringColumnEncryptor.class)
    private String suffix;

    public Address(int id,
                   String countryCode,
                   String postalCode,
                   String city,
                   String street,
                   String streetNumber,
                   String suffix) {

        this.id = id;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.suffix = suffix;
    }

    public Address(String countryCode,
                   String postalCode,
                   String city,
                   String street,
                   String streetNumber,
                   String suffix) {

        this(0, countryCode, postalCode, city, street, streetNumber, suffix);
    }

    public Address() {

        this("", "", "", "", "", "");
    }

    @Override
    public Integer getID() {

        return id;
    }

    @Override
    public void setID(Integer id) {

        this.id = id;
    }


    public String getCountryCode() {

        return countryCode;
    }

    public void setCountryCode(String countryCode) {

        this.countryCode = countryCode;
    }

    public String getPostalCode() {

        return postalCode;
    }

    public void setPostalCode(String postalCode) {

        this.postalCode = postalCode;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getStreet() {

        return street;
    }

    public void setStreet(String street) {

        this.street = street;
    }

    public String getStreetNumber() {

        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {

        this.streetNumber = streetNumber;
    }

    public String getSuffix() {

        return suffix;
    }

    public void setSuffix(String suffix) {

        this.suffix = suffix;
    }

    @Override
    public String toString() {

        return "Address{" +
                "id=" + getID() +
                ", countryCode='" + getCountryCode() + '\'' +
                ", postalCode='" + getPostalCode() + '\'' +
                ", city='" + getCity() + '\'' +
                ", street='" + getStreet() + '\'' +
                ", streetNumber='" + getStreetNumber() + '\'' +
                ", suffix='" + getSuffix() + '\'' +
                '}';
    }
}
