package de.code.junction.feldberger.mgmt.data.access.user;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import jakarta.persistence.*;

@Entity(name = "users")
@SuppressWarnings("unused")
public class User implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "password_salt", nullable = false)
    private String passwordSalt;

    @Column(nullable = false)
    private boolean active;

    public User() {
        this(0, "", "", "", false);
    }

    public User(int id, String username, String passwordHash, String passwordSalt, boolean active) {

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.active = active;
    }

    public User(String username, String passwordHash, String passwordSalt) {
        this(0, username, passwordHash, passwordSalt, true);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public boolean isActive() {
        return active;
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


    @Override
    public String toString() {

        return "User{" +
                "id=" + id +
                ", username='" + getUsername() + '\'' +
                ", passwordHash='" + getPasswordHash() + '\'' +
                ", passwordSalt='" + getPasswordSalt() + '\'' +
                ", active=" + isActive() +
                '}';
    }
}
