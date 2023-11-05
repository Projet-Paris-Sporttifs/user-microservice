package psp.user.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import psp.user.validator.GenderValidation;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequest {

    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username is invalid")
    @Size(max = 50, message = "Username is too long")
    @Size(min = 5, message = "Username is too short")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Invalid password. The password must contains at least 8 characters, "
                    + "including one letter, one digit, and one special character (@#$%^&+=!).")
    @NotBlank(message = "Password is mandatory")
    @Size(max = 32, message = "Password is too long")
    private String password;

    @NotBlank(message = "Password confirmation is mandatory")
    private String passwordConfirm;

    @NotBlank(message = "Email is mandatory")
    @Size(max = 250, message = "Email address is too long")
    @Email(message = "Email address is invalid")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 50, message = "Phone number is too long")
    private String phone;

    @NotBlank(message = "Gender is mandatory")
    @GenderValidation
    private String gender;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 250, message = "First name is too long")
    private String firstname;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 250, message = "Last name is too long")
    private String lastname;

}
