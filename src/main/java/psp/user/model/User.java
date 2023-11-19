package psp.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.*;

@Data
@Entity
@DynamicUpdate
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

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

    @Column(nullable = false, length = 6)
    private String gender;

    @Column(nullable = false, length = 250)
    private String firstname;

    @Column(nullable = false, length = 250)
    private String lastname;

    @JsonIgnore
    @Column(nullable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(nullable = false)
    private Date updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    @PrePersist
    public void onCreate() {
        processData();
        updatedAt = new Date();
        createdAt = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        processData();
        updatedAt = new Date();
    }

    private void processData() {
        setPhone(getPhone().replaceAll("\\s", ""));
        setFirstname(getFirstname().trim().replaceAll("\\s+", " "));
        setLastname(getLastname().trim().replaceAll("\\s+", " "));
    }
}
