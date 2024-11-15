package de.code.junction.feldberger.mgmt.data.access.usercontact;

import de.code.junction.feldberger.mgmt.data.converter.StringColumnEncryptor;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.NonNull;

@Entity(name = "user_contacts")
public class UserContact {

    @Convert(converter = StringColumnEncryptor.class)
    private @NonNull String firstName;
}
