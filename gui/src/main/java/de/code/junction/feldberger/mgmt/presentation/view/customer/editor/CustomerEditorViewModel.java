package de.code.junction.feldberger.mgmt.presentation.view.customer.editor;

import de.code.junction.feldberger.mgmt.data.access.address.Address;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute;
import de.code.junction.feldberger.mgmt.presentation.messaging.MessageResponse;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messages;
import de.code.junction.feldberger.mgmt.presentation.messaging.Messenger;
import de.code.junction.feldberger.mgmt.presentation.navigation.Navigator;
import de.code.junction.feldberger.mgmt.presentation.view.FXViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.concurrent.WorkerStateEvent;

import java.time.LocalDateTime;

import static de.code.junction.feldberger.mgmt.presentation.components.main.menu.MainMenuRoute.CustomerDashboard;

public class CustomerEditorViewModel extends FXViewModel {

    private final IntegerProperty id;
    private final IntegerProperty addressId;

    private final StringProperty currentIdNo;
    private final StringProperty currentLastName;
    private final StringProperty currentFirstName;
    private final StringProperty currentCompanyName;
    private final StringProperty currentEmailAddress;
    private final StringProperty currentLandlinePhoneNumber;
    private final StringProperty currentMobilePhoneNumber;
    private final StringProperty currentNotes;
    private final StringProperty currentCountryCode;
    private final StringProperty currentPostalCode;
    private final StringProperty currentCity;
    private final StringProperty currentStreet;
    private final StringProperty currentStreetNumber;
    private final StringProperty currentSuffix;

    private final BooleanProperty archived;
    private final ObjectProperty<LocalDateTime> archiveDate;

    private final StringProperty newIdNo;
    private final StringProperty newLastName;
    private final StringProperty newFirstName;
    private final StringProperty newCompanyName;
    private final StringProperty newEmailAddress;
    private final StringProperty newLandlinePhoneNumber;
    private final StringProperty newMobilePhoneNumber;
    private final StringProperty newNotes;
    private final StringProperty newCountryCode;
    private final StringProperty newPostalCode;
    private final StringProperty newCity;
    private final StringProperty newStreet;
    private final StringProperty newStreetNumber;
    private final StringProperty newSuffix;

    private final BooleanProperty dirty;
    private final BooleanProperty saveDisabled;

    private final ObjectProperty<Customer> customer;
    private final ObjectProperty<Address> address;

    private final Navigator<MainMenuRoute> navigator;
    private final MainMenuRoute backRoute;

    private final CustomerSavingService customerSavingService;

    public CustomerEditorViewModel(Messenger messenger,
                                   Navigator<MainMenuRoute> navigator,
                                   CustomerSavingService customerSavingService, MainMenuRoute backRoute,
                                   Customer customer) {

        super(messenger);

        this.navigator = navigator;
        this.backRoute = backRoute;
        this.customerSavingService = customerSavingService;

        id = new SimpleIntegerProperty(customer.getId());
        currentIdNo = new SimpleStringProperty(customer.getIdNo());
        newIdNo = new SimpleStringProperty(customer.getIdNo());
        currentLastName = new SimpleStringProperty(customer.getLastName());
        newLastName = new SimpleStringProperty(customer.getLastName());
        currentFirstName = new SimpleStringProperty(customer.getFirstName());
        newFirstName = new SimpleStringProperty(customer.getFirstName());
        currentCompanyName = new SimpleStringProperty(customer.getCompanyName());
        newCompanyName = new SimpleStringProperty(customer.getCompanyName());
        currentEmailAddress = new SimpleStringProperty(customer.getEmailAddress());
        newEmailAddress = new SimpleStringProperty(customer.getEmailAddress());
        currentLandlinePhoneNumber = new SimpleStringProperty(customer.getLandlinePhoneNumber());
        newLandlinePhoneNumber = new SimpleStringProperty(customer.getLandlinePhoneNumber());
        currentMobilePhoneNumber = new SimpleStringProperty(customer.getMobilePhoneNumber());
        newMobilePhoneNumber = new SimpleStringProperty(customer.getMobilePhoneNumber());
        currentNotes = new SimpleStringProperty(customer.getNotes());
        newNotes = new SimpleStringProperty(customer.getNotes());
        archived = new SimpleBooleanProperty(customer.isArchived());
        archiveDate = new SimpleObjectProperty<>(customer.getArchiveDate());

        final var address = customer.getAddress();
        addressId = new SimpleIntegerProperty(address.getId());
        currentCountryCode = new SimpleStringProperty(address.getCountryCode());
        newCountryCode = new SimpleStringProperty(address.getCountryCode());
        currentPostalCode = new SimpleStringProperty(address.getPostalCode());
        newPostalCode = new SimpleStringProperty(address.getPostalCode());
        currentCity = new SimpleStringProperty(address.getCity());
        newCity = new SimpleStringProperty(address.getCity());
        currentStreet = new SimpleStringProperty(address.getStreet());
        newStreet = new SimpleStringProperty(address.getStreet());
        currentStreetNumber = new SimpleStringProperty(address.getStreetNumber());
        newStreetNumber = new SimpleStringProperty(address.getStreetNumber());
        currentSuffix = new SimpleStringProperty(address.getSuffix());
        newSuffix = new SimpleStringProperty(address.getSuffix());

        dirty = new SimpleBooleanProperty(false);
        saveDisabled = new SimpleBooleanProperty(true);
        this.customer = new SimpleObjectProperty<>();
        this.address = new SimpleObjectProperty<>();
    }

    @Override
    public void init() {

        dirty.bind(currentIdNo.isNotEqualTo(newIdNo)
                .or(currentLastName.isNotEqualTo(newLastName))
                .or(currentFirstName.isNotEqualTo(newFirstName))
                .or(currentCompanyName.isNotEqualTo(newCompanyName))
                .or(currentEmailAddress.isNotEqualTo(newEmailAddress))
                .or(currentLandlinePhoneNumber.isNotEqualTo(newLandlinePhoneNumber))
                .or(currentMobilePhoneNumber.isNotEqualTo(newMobilePhoneNumber))
                .or(currentNotes.isNotEqualTo(newNotes))
                .or(currentCountryCode.isNotEqualTo(newCountryCode))
                .or(currentPostalCode.isNotEqualTo(newPostalCode))
                .or(currentCity.isNotEqualTo(newCity))
                .or(currentStreet.isNotEqualTo(newStreet))
                .or(currentStreetNumber.isNotEqualTo(newStreetNumber))
                .or(currentSuffix.isNotEqualTo(newSuffix)));

        saveDisabled.bind(dirty.not());

        address.bind(Bindings.createObjectBinding(
                () -> new Address(
                        getAddressId(),
                        getNewCountryCode(),
                        getNewPostalCode(),
                        getNewCity(),
                        getNewStreet(),
                        getNewStreetNumber(),
                        getNewSuffix()
                ),
                addressId,
                newCountryCode,
                newPostalCode,
                newCity,
                newStreet,
                newStreetNumber,
                newSuffix
        ));

        customer.bind(Bindings.createObjectBinding(
                () -> new Customer(
                        getId(),
                        getNewIdNo(),
                        getNewLastName(),
                        getNewFirstName(),
                        getNewCompanyName(),
                        getNewEmailAddress(),
                        getNewLandlinePhoneNumber(),
                        getNewMobilePhoneNumber(),
                        address.get(),
                        getNewNotes(),
                        isArchived(),
                        getArchiveDate()
                ),
                id,
                newIdNo,
                newLastName,
                newFirstName,
                newCompanyName,
                newEmailAddress,
                newLandlinePhoneNumber,
                newMobilePhoneNumber,
                address,
                newNotes,
                archived,
                archiveDate
        ));

        customerSavingService.customerProperty().bind(customer);
        customerSavingService.setOnSucceeded(this::onCustomerSavingServiceSucceeded);
    }

    public void onBackClicked() {

        final Runnable backTransition = () -> navigator.navigateTo(backRoute);

        if (!isDirty()) {
            backTransition.run();
            return;
        }

        messenger().send(
                Messages.CUSTOMER_EDITOR_CONFIRM_UNSAVED_CHANGES,
                response -> {
                    if (response == MessageResponse.PROCEED)
                        backTransition.run();
                }
        );
    }

    public void onSaveClicked() {

        if (isSaveDisabled())
            return;

        customerSavingService.restart();
    }

    private void onCustomerSavingServiceSucceeded(WorkerStateEvent event) {

        switch (customerSavingService.getValue()) {
            case CustomerSaveResult.Created(Customer customer) -> navigator.navigateTo(new CustomerDashboard(customer));
            case CustomerSaveResult.Updated() -> navigator.navigateTo(new CustomerDashboard(getCustomer()));
            case CustomerSaveResult.Failure failure -> messenger().send(failure.message());
        }
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

    public String getCurrentIdNo() {
        return currentIdNo.get();
    }

    public StringProperty currentIdNoProperty() {
        return currentIdNo;
    }

    public void setCurrentIdNo(String currentIdNo) {
        this.currentIdNo.set(currentIdNo);
    }

    public String getNewIdNo() {
        return newIdNo.get();
    }

    public StringProperty newIdNoProperty() {
        return newIdNo;
    }

    public void setNewIdNo(String newIdNo) {
        this.newIdNo.set(newIdNo);
    }

    public String getCurrentLastName() {
        return currentLastName.get();
    }

    public StringProperty currentLastNameProperty() {
        return currentLastName;
    }

    public void setCurrentLastName(String currentLastName) {
        this.currentLastName.set(currentLastName);
    }

    public String getNewLastName() {
        return newLastName.get();
    }

    public StringProperty newLastNameProperty() {
        return newLastName;
    }

    public void setNewLastName(String newLastName) {
        this.newLastName.set(newLastName);
    }

    public String getCurrentFirstName() {
        return currentFirstName.get();
    }

    public StringProperty currentFirstNameProperty() {
        return currentFirstName;
    }

    public void setCurrentFirstName(String currentFirstName) {
        this.currentFirstName.set(currentFirstName);
    }

    public String getNewFirstName() {
        return newFirstName.get();
    }

    public StringProperty newFirstNameProperty() {
        return newFirstName;
    }

    public void setNewFirstName(String newFirstName) {
        this.newFirstName.set(newFirstName);
    }

    public String getCurrentCompanyName() {
        return currentCompanyName.get();
    }

    public StringProperty currentCompanyNameProperty() {
        return currentCompanyName;
    }

    public void setCurrentCompanyName(String currentCompanyName) {
        this.currentCompanyName.set(currentCompanyName);
    }

    public String getNewCompanyName() {
        return newCompanyName.get();
    }

    public StringProperty newCompanyNameProperty() {
        return newCompanyName;
    }

    public void setNewCompanyName(String newCompanyName) {
        this.newCompanyName.set(newCompanyName);
    }

    public String getCurrentEmailAddress() {
        return currentEmailAddress.get();
    }

    public StringProperty currentEmailAddressProperty() {
        return currentEmailAddress;
    }

    public void setCurrentEmailAddress(String currentEmailAddress) {
        this.currentEmailAddress.set(currentEmailAddress);
    }

    public String getNewEmailAddress() {
        return newEmailAddress.get();
    }

    public StringProperty newEmailAddressProperty() {
        return newEmailAddress;
    }

    public void setNewEmailAddress(String newEmailAddress) {
        this.newEmailAddress.set(newEmailAddress);
    }

    public String getCurrentLandlinePhoneNumber() {
        return currentLandlinePhoneNumber.get();
    }

    public StringProperty currentLandlinePhoneNumberProperty() {
        return currentLandlinePhoneNumber;
    }

    public void setCurrentLandlinePhoneNumber(String currentLandlinePhoneNumber) {
        this.currentLandlinePhoneNumber.set(currentLandlinePhoneNumber);
    }

    public String getNewLandlinePhoneNumber() {
        return newLandlinePhoneNumber.get();
    }

    public StringProperty newLandlinePhoneNumberProperty() {
        return newLandlinePhoneNumber;
    }

    public void setNewLandlinePhoneNumber(String newLandlinePhoneNumber) {
        this.newLandlinePhoneNumber.set(newLandlinePhoneNumber);
    }

    public String getCurrentMobilePhoneNumber() {
        return currentMobilePhoneNumber.get();
    }

    public StringProperty currentMobilePhoneNumberProperty() {
        return currentMobilePhoneNumber;
    }

    public void setCurrentMobilePhoneNumber(String currentMobilePhoneNumber) {
        this.currentMobilePhoneNumber.set(currentMobilePhoneNumber);
    }

    public String getNewMobilePhoneNumber() {
        return newMobilePhoneNumber.get();
    }

    public StringProperty newMobilePhoneNumberProperty() {
        return newMobilePhoneNumber;
    }

    public void setNewMobilePhoneNumber(String newMobilePhoneNumber) {
        this.newMobilePhoneNumber.set(newMobilePhoneNumber);
    }

    public String getCurrentNotes() {
        return currentNotes.get();
    }

    public StringProperty currentNotesProperty() {
        return currentNotes;
    }

    public void setCurrentNotes(String currentNotes) {
        this.currentNotes.set(currentNotes);
    }

    public String getNewNotes() {
        return newNotes.get();
    }

    public StringProperty newNotesProperty() {
        return newNotes;
    }

    public void setNewNotes(String newNotes) {
        this.newNotes.set(newNotes);
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

    public String getCurrentCountryCode() {
        return currentCountryCode.get();
    }

    public StringProperty currentCountryCodeProperty() {
        return currentCountryCode;
    }

    public void setCurrentCountryCode(String currentCountryCode) {
        this.currentCountryCode.set(currentCountryCode);
    }

    public String getNewCountryCode() {
        return newCountryCode.get();
    }

    public StringProperty newCountryCodeProperty() {
        return newCountryCode;
    }

    public void setNewCountryCode(String newCountryCode) {
        this.newCountryCode.set(newCountryCode);
    }

    public String getCurrentPostalCode() {
        return currentPostalCode.get();
    }

    public StringProperty currentPostalCodeProperty() {
        return currentPostalCode;
    }

    public void setCurrentPostalCode(String currentPostalCode) {
        this.currentPostalCode.set(currentPostalCode);
    }

    public String getNewPostalCode() {
        return newPostalCode.get();
    }

    public StringProperty newPostalCodeProperty() {
        return newPostalCode;
    }

    public void setNewPostalCode(String newPostalCode) {
        this.newPostalCode.set(newPostalCode);
    }

    public String getCurrentCity() {
        return currentCity.get();
    }

    public StringProperty currentCityProperty() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity.set(currentCity);
    }

    public String getNewCity() {
        return newCity.get();
    }

    public StringProperty newCityProperty() {
        return newCity;
    }

    public void setNewCity(String newCity) {
        this.newCity.set(newCity);
    }

    public String getCurrentStreet() {
        return currentStreet.get();
    }

    public StringProperty currentStreetProperty() {
        return currentStreet;
    }

    public void setCurrentStreet(String currentStreet) {
        this.currentStreet.set(currentStreet);
    }

    public String getNewStreet() {
        return newStreet.get();
    }

    public StringProperty newStreetProperty() {
        return newStreet;
    }

    public void setNewStreet(String newStreet) {
        this.newStreet.set(newStreet);
    }

    public String getCurrentStreetNumber() {
        return currentStreetNumber.get();
    }

    public StringProperty currentStreetNumberProperty() {
        return currentStreetNumber;
    }

    public void setCurrentStreetNumber(String currentStreetNumber) {
        this.currentStreetNumber.set(currentStreetNumber);
    }

    public String getNewStreetNumber() {
        return newStreetNumber.get();
    }

    public StringProperty newStreetNumberProperty() {
        return newStreetNumber;
    }

    public void setNewStreetNumber(String newStreetNumber) {
        this.newStreetNumber.set(newStreetNumber);
    }

    public String getCurrentSuffix() {
        return currentSuffix.get();
    }

    public StringProperty currentSuffixProperty() {
        return currentSuffix;
    }

    public void setCurrentSuffix(String currentSuffix) {
        this.currentSuffix.set(currentSuffix);
    }

    public String getNewSuffix() {
        return newSuffix.get();
    }

    public StringProperty newSuffixProperty() {
        return newSuffix;
    }

    public void setNewSuffix(String newSuffix) {
        this.newSuffix.set(newSuffix);
    }

    public boolean isDirty() {
        return dirty.get();
    }

    public BooleanProperty dirtyProperty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty.set(dirty);
    }

    public boolean isSaveDisabled() {
        return saveDisabled.get();
    }

    public BooleanProperty saveDisabledProperty() {
        return saveDisabled;
    }

    public void setSaveDisabled(boolean saveDisabled) {
        this.saveDisabled.set(saveDisabled);
    }

    public Address getAddress() {
        return address.get();
    }

    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }
}
