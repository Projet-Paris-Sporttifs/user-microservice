package psp.user.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String type = "JWT";

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
