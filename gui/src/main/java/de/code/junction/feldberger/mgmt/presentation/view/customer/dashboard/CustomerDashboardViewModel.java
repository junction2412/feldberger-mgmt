package de.code.junction.feldberger.mgmt.presentation.view.customer.dashboard;

import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.*;

public class CustomerDashboardViewModel extends FXViewModel {

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
    private final IntegerProperty addressId;
    private final StringProperty countryCode;
    private final StringProperty postalCode;
    private final StringProperty city;
    private final StringProperty street;
    private final StringProperty streetNumber;
    private final StringProperty suffix;

    private final ListProperty<Transaction> transactions;
    private final ObjectProperty<Transaction> selectedTransaction;

    private final ObjectProperty<Customer> customer;

    private final Navigator<MainMenuRoute> navigator;

    public CustomerDashboardViewModel(Messenger messenger, Navigator<MainMenuRoute> navigator, Customer customer) {

        super(messenger);
        this.navigator = navigator;

        id = new SimpleIntegerProperty(customer.getId());
        idNo = new SimpleStringProperty(customer.getIdNo());
        lastName = new SimpleStringProperty(customer.getLastName());
        firstName = new SimpleStringProperty(customer.getFirstName());
        companyName = new SimpleStringProperty(customer.getCompanyName());
        emailAddress = new SimpleStringProperty(customer.getEmailAddress());
        landlinePhoneNumber = new SimpleStringProperty(customer.getLandlinePhoneNumber());
        mobilePhoneNumber = new SimpleStringProperty(customer.getMobilePhoneNumber());
        notes = new SimpleStringProperty(customer.getNotes());
        archived = new SimpleBooleanProperty(customer.isArchived());
        archiveDate = new SimpleObjectProperty<>(customer.getArchiveDate());

        final var address = customer.getAddress();

        addressId = new SimpleIntegerProperty(address.getId());
        countryCode = new SimpleStringProperty(address.getCountryCode());
        postalCode = new SimpleStringProperty(address.getPostalCode());
        city = new SimpleStringProperty(address.getCity());
        street = new SimpleStringProperty(address.getStreet());
        streetNumber = new SimpleStringProperty(address.getStreetNumber());
        suffix = new SimpleStringProperty(address.getSuffix());

        transactions = new SimpleListProperty<>(FXCollections.emptyObservableList());
        selectedTransaction = new SimpleObjectProperty<>();

        this.customer = new SimpleObjectProperty<>(customer);
    }

    @Override
    public void init() {
        System.out.println("init");
    }

    public void onBackClicked() {
        navigator.navigateTo(new CustomerOverview());
    }

    public void onEditClicked() {
        final var customer = getCustomer();
        navigator.navigateTo(new CustomerEditor(new CustomerDashboard(customer), customer));
    }

    public void onCreateTransactionClicked() {
        System.out.println("Create transaction clicked");
    }

    public void onTransactionClicked(int clickCount) {

        final var transaction = getSelectedTransaction();

        if (clickCount != 2 || transaction == null)
            return;

        System.out.println("Transaction double clicked");
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

    public int getAddressId() {
        return addressId.get();
    }

    public IntegerProperty addressIdProperty() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
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

    public ObservableList<Transaction> getTransactions() {
        return transactions.get();
    }

    public ListProperty<Transaction> transactionsProperty() {
        return transactions;
    }

    public void setTransactions(ObservableList<Transaction> transactions) {
        this.transactions.set(transactions);
    }

    public Transaction getSelectedTransaction() {
        return selectedTransaction.get();
    }

    public ObjectProperty<Transaction> selectedTransactionProperty() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction.set(selectedTransaction);
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }
}
