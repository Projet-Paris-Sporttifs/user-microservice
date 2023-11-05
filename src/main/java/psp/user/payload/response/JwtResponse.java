package psp.user.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String type = "JWT";

    private String token;

    private String username;

    private String email;

    private Long id;

    private List<String> roles;

    public JwtResponse(String token, String username, String email, Long id, List<String> roles) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.id = id;
        this.roles = roles;
    }

}
