package psp.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import psp.user.validator.GenderValidation;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username is invalid")
    @Size(max = 50, message = "Username is too long")
    @Size(min = 5, message = "Username is too short")
    private String username;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is mandatory")
    @Size(max = 180, message = "Email address is too long")
    @Email(message = "Email address is invalid")
    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 18, message = "Phone number is too long")
    private String phone;

    @Column(nullable = false)
    @NotBlank(message = "Gender is mandatory")
    @GenderValidation
    private String gender;

    @Column(nullable = false)
    @NotBlank(message = "First name is mandatory")
    @Size(max = 100, message = "First name is too long")
    private String firstname;

    @Column(nullable = false)
    @NotBlank(message = "Last name is mandatory")
    @Size(max = 100, message = "Last name is too long")
    private String lastname;

    @PrePersist
    public void processData() {
        setPhone(getPhone().replaceAll("\\s", ""));
        setFirstname(getFirstname().trim().replaceAll("\\s+", " "));
        setLastname(getLastname().trim().replaceAll("\\s+", " "));
    }
}
