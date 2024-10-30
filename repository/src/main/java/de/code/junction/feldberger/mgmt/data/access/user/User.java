package de.code.junction.feldberger.mgmt.data.access.user;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Getter @Setter
@ToString @EqualsAndHashCode
@SuppressWarnings("unused")
public class User implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private @NonNull String username;

    @Column(name = "password_hash", nullable = false)
    private @NonNull String passwordHash;

    @Column(name = "password_salt", nullable = false)
    private @NonNull String passwordSalt;

    @Column(nullable = false)
    private boolean active;

    public User() {

        this(0, "", "", "", false);
    }

    public User(int id,
                @NonNull String username,
                @NonNull String passwordHash,
                @NonNull String passwordSalt,
                boolean active) {

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.active = active;
    }

    public User(@NonNull String username,
                @NonNull String passwordHash,
                @NonNull String passwordSalt) {

        this(0, username, passwordHash, passwordSalt, true);
    }

    @Override
    public Integer getID() {

        return id;
    }

    @Override
    public void setID(Integer id) {

        this.id = id;
    }

    public void setActive() {

        if (!active)
            active = true;
    }

    public boolean isInactive() {

        return !active;
    }

    public void setInactive() {

        if (active)
            active = false;
    }
}
