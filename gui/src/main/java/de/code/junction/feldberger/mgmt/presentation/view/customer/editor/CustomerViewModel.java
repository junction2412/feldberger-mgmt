package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class CustomerViewModel {

    private final Customer originalState;

    private final IntegerProperty id;
    private final StringProperty idNo;
    private final StringProperty lastName;
    private final StringProperty firstName;
    private final StringProperty companyName;
    private final StringProperty emailAddress;
    private final StringProperty landlinePhoneNumber;
    private final StringProperty mobilePhoneNumber;
    private final StringProperty notes;
    private final BooleanProperty archived;
    private final ObjectProperty<LocalDateTime> archiveDate;

    private final AddressViewModel addressViewModel;

    public CustomerViewModel(int id,
                             String idNo,
                             String lastName,
                             String firstName,
                             String companyName,
                             String emailAddress,
                             String landlinePhoneNumber,
                             String mobilePhoneNumber,
                             String notes,
                             boolean archived,
                             LocalDateTime archiveDate,
                             AddressViewModel addressViewModel) {

        this.id = new SimpleIntegerProperty(id);
        this.idNo = new SimpleStringProperty(idNo);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.companyName = new SimpleStringProperty(companyName);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.landlinePhoneNumber = new SimpleStringProperty(landlinePhoneNumber);
        this.mobilePhoneNumber = new SimpleStringProperty(mobilePhoneNumber);
        this.notes = new SimpleStringProperty(notes);
        this.archived = new SimpleBooleanProperty(archived);
        this.archiveDate = new SimpleObjectProperty<>(archiveDate);
        this.addressViewModel = addressViewModel;

        this.originalState = toCustomer();
    }

    public CustomerViewModel(Customer customer) {

        this(
                customer.getId(),
                customer.getIdNo(),
                customer.getLastName(),
                customer.getFirstName(),
                customer.getCompanyName(),
                customer.getEmailAddress(),
                customer.getLandlinePhoneNumber(),
                customer.getMobilePhoneNumber(),
                customer.getNotes(),
                customer.isArchived(),
                customer.getArchiveDate(),
                (customer.getAddress() != null) ? new AddressViewModel(customer.getAddress()) : new AddressViewModel()
        );
    }

    public CustomerViewModel() {

        this(
                0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                null,
                new AddressViewModel()
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

    public String getIdNo() {

        return idNo.get();
    }

    public StringProperty idNoProperty() {

        return idNo;
    }

    public void setIdNo(String idNo) {

        this.idNo.set(idNo);
    }

    public String getLastName() {

        return lastName.get();
    }

    public StringProperty lastNameProperty() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName.set(lastName);
    }

    public String getFirstName() {

        return firstName.get();
    }

    public StringProperty firstNameProperty() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName.set(firstName);
    }

    public String getCompanyName() {

        return companyName.get();
    }

    public StringProperty companyNameProperty() {

        return companyName;
    }

    public void setCompanyName(String companyName) {

        this.companyName.set(companyName);
    }

    public String getEmailAddress() {

        return emailAddress.get();
    }

    public StringProperty emailAddressProperty() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress.set(emailAddress);
    }

    public String getLandlinePhoneNumber() {

        return landlinePhoneNumber.get();
    }

    public StringProperty landlinePhoneNumberProperty() {

        return landlinePhoneNumber;
    }

    public void setLandlinePhoneNumber(String landlinePhoneNumber) {

        this.landlinePhoneNumber.set(landlinePhoneNumber);
    }

    public String getMobilePhoneNumber() {

        return mobilePhoneNumber.get();
    }

    public StringProperty mobilePhoneNumberProperty() {

        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {

        this.mobilePhoneNumber.set(mobilePhoneNumber);
    }

    public String getNotes() {

        return notes.get();
    }

    public StringProperty notesProperty() {

        return notes;
    }

    public void setNotes(String notes) {

        this.notes.set(notes);
    }

    public boolean isArchived() {

        return archived.get();
    }

    public BooleanProperty archivedProperty() {

        return archived;
    }

    public void setArchived(boolean archived) {

        this.archived.set(archived);
    }

    public LocalDateTime getArchiveDate() {

        return archiveDate.get();
    }

    public ObjectProperty<LocalDateTime> archiveDateProperty() {

        return archiveDate;
    }

    public void setArchiveDate(LocalDateTime archiveDate) {

        this.archiveDate.set(archiveDate);
    }

    public AddressViewModel getAddressViewModel() {

        return addressViewModel;
    }

    public Customer toCustomer() {

        return new Customer(
                getId(),
                getIdNo(),
                getLastName(),
                getFirstName(),
                getCompanyName(),
                getEmailAddress(),
                getLandlinePhoneNumber(),
                getMobilePhoneNumber(),
                getAddressViewModel().toAddress(),
                getNotes(),
                isArchived(),
                getArchiveDate()
        );
    }

    public Customer getOriginalState() {

        return new Customer(
                originalState.getId(),
                originalState.getIdNo(),
                originalState.getLastName(),
                originalState.getFirstName(),
                originalState.getCompanyName(),
                originalState.getEmailAddress(),
                originalState.getLandlinePhoneNumber(),
                originalState.getMobilePhoneNumber(),
                originalState.getAddress(),
                originalState.getNotes(),
                originalState.isArchived(),
                originalState.getArchiveDate()
        );
    }
}
