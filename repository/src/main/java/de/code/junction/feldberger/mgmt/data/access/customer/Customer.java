package de.code.junction.feldberger.mgmt.data.access.customer;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.converter.StringColumnEncryptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "customers")
public class Customer implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "last_name")
    @Convert(converter = StringColumnEncryptor.class)
    private String lastName;

    @Column(name = "first_name")
    @Convert(converter = StringColumnEncryptor.class)
    private String firstName;

    @Column(name = "email_address")
    @Convert(converter = StringColumnEncryptor.class)
    private String emailAddress;

    @Column(name = "landline_phone_number")
    @Convert(converter = StringColumnEncryptor.class)
    private String landlinePhoneNumber;

    @Column(name = "mobile_phone_number")
    @Convert(converter = StringColumnEncryptor.class)
    private String mobilePhoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @Column(nullable = false)
    private boolean archived;

    @Column(name = "archive_date")
    private LocalDateTime archiveDate;

    @Override
    public Integer getID() {

        return id;
    }

    @Override
    public void setID(Integer id) {

        this.id = id;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }

    public String getLandlinePhoneNumber() {

        return landlinePhoneNumber;
    }

    public void setLandlinePhoneNumber(String landlinePhoneNumber) {

        this.landlinePhoneNumber = landlinePhoneNumber;
    }

    public String getMobilePhoneNumber() {

        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {

        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Address getAddress() {

        return address;
    }

    public void setAddress(Address address) {

        this.address = address;
    }

    public boolean isArchived() {

        return archived;
    }

    public void setArchived(boolean archived) {

        this.archived = archived;
    }

    public LocalDateTime getArchiveDate() {

        return archiveDate;
    }

    public void setArchiveDate(LocalDateTime archiveDate) {

        this.archiveDate = archiveDate;
    }
}
