package es.ulpgc.LectioBackend.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3)
    @Column(name = "nombre", nullable = false)
    private String firstName;

    @Size(min = 3)
    @Column(name = "apellidos", nullable = false)
    private String lastName;

    @Size(min = 3)
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 3)
    @Column(name = "clave", nullable = false)
    private String password;

    @Column(name = "photo", nullable = true)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "datos_adicionales", nullable = true)
    private String additional;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String photo, Rol rol, String additional) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = encodePassword(password);
        this.photo = photo;
        this.rol = rol;
        this.additional = additional;
    }

    private String encodePassword(String password) {
        if (password.length() > 2) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);
        }
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        this.password = encodePassword(password);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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
        setPassword(user.getPassword());
        setPhoto(user.getPhoto());
        setRol(user.getRol());
        setAdditional(user.getAdditional());
    }
}
