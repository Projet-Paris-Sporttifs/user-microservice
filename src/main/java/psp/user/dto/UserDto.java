package psp.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String username;

    private String password;

    private String passwordConfirm;

    private String email;

    private String phone;

    private String gender;

    private String firstname;

    private String lastname;
}
