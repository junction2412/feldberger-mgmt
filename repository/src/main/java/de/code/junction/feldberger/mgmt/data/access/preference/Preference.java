package de.code.junction.feldberger.mgmt.data.access.preference;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import jakarta.persistence.*;

@Entity(name = "preferences")
@IdClass(PreferenceId.class)
public class Preference implements DataTransferObject<PreferenceId> {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Id
    private String scope;

    @Id
    @Column(nullable = false, length = 1024)
    private String name;

    @Column(length = 1024)
    private String preferredValue;

    public Preference(User user, String scope, String name, String preferredValue) {

        this.user = user;
        this.scope = scope;
        this.name = name;
        this.preferredValue = preferredValue;
    }

    public Preference(PreferenceId id, String preferredValue) {

        this(id.user(), id.scope(), id.name(), preferredValue);
    }

    public Preference() {

        this(null, "", "", null);
    }

    @Override
    public PreferenceId getId() {

        return new PreferenceId(getUser(), getScope(), getName());
    }

    @Override
    public void setId(PreferenceId id) {

        setUser(id.user());
        setScope(id.scope());
        setName(id.name());
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getScope() {

        return scope;
    }

    public void setScope(String scope) {

        this.scope = scope;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPreferredValue() {

        return preferredValue;
    }

    public void setPreferredValue(String preferredValue) {

        this.preferredValue = preferredValue;
    }

}
