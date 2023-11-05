package psp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 120)
    private String password;

    @JsonIgnore
    @Transient
    private String passwordConfirm;

    @Column(unique = true, nullable = false, length = 250)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    public User() {
    }

    @PrePersist
    public void processData() {
        setPhone(getPhone().replaceAll("\\s", ""));
        setFirstname(getFirstname().trim().replaceAll("\\s+", " "));
        setLastname(getLastname().trim().replaceAll("\\s+", " "));
    }
}
