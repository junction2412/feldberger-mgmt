package de.code.junction.feldberger.mgmt.data.access.customer;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.data.converter.StringColumnEncryptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "customers")
public class Customer implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "idno", unique = true, length = 64)
    private String idNo;

    @Column(name = "last_name", length = 1024)
    @Convert(converter = StringColumnEncryptor.class)
    private String lastName;

    @Column(name = "first_name", length = 1024)
    @Convert(converter = StringColumnEncryptor.class)
    private String firstName;

    @Column(name = "company_name", length = 1024)
    @Convert(converter = StringColumnEncryptor.class)
    private String companyName;

    @Column(name = "email_address", length = 1024)
    @Convert(converter = StringColumnEncryptor.class)
    private String emailAddress;

    @Column(name = "landline_phone_number", length = 512)
    @Convert(converter = StringColumnEncryptor.class)
    private String landlinePhoneNumber;

    @Column(name = "mobile_phone_number", length = 512)
    @Convert(converter = StringColumnEncryptor.class)
    private String mobilePhoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @Column(length = 2048)
    @Convert(converter = StringColumnEncryptor.class)
    private String notes;

    @Column(nullable = false)
    private boolean archived;

    @Column(name = "archive_date")
    private LocalDateTime archiveDate;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    public Customer(int id,
                    String idNo,
                    String lastName,
                    String firstName,
                    String companyName,
                    String emailAddress,
                    String landlinePhoneNumber,
                    String mobilePhoneNumber,
                    Address address,
                    String notes,
                    boolean archived,
                    LocalDateTime archiveDate) {

        this.id = id;
        this.idNo = idNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.companyName = companyName;
        this.emailAddress = emailAddress;
        this.landlinePhoneNumber = landlinePhoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.address = address;
        this.notes = notes;
        this.archived = archived;
        this.archiveDate = archiveDate;
    }

    public Customer(String idNo,
                    String lastName,
                    String firstName,
                    String companyName,
                    String emailAddress,
                    String landlinePhoneNumber,
                    String mobilePhoneNumber,
                    Address address) {

        this(0, idNo, lastName, firstName, companyName, emailAddress, landlinePhoneNumber, mobilePhoneNumber, address,
                "", false, null);
    }

    public Customer() {

        this("", "", "", "", "", "", "",
                null);
    }

    @Override
    public Integer getId() {

        return id;
    }

    @Override
    public void setId(Integer id) {

        this.id = id;
    }

    public String getIdNo() {

        return idNo;
    }

    public void setIdNo(String idNo) {

        this.idNo = idNo;
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

    public String getCompanyName() {

        return companyName;
    }

    public void setCompanyName(String companyName) {

        this.companyName = companyName;
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

    public String getNotes() {

        return notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;
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

    public List<Transaction> getTransactions() {

        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {

        this.transactions = transactions;
    }
}
