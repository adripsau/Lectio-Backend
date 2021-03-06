package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Size(min = 3)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 3)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(min = 3)
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 3)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo", nullable = true)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Rol role;

    @Column(name = "additional", nullable = true)
    private String additional;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String photo, Rol role, String additional) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.role = role;
        this.additional = additional;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public void updateAll(User user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setPhoto(user.getPhoto());
        setRole(user.getRole());
        setAdditional(user.getAdditional());
    }
}
