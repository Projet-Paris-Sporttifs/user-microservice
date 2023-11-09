package psp.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import psp.user.model.User;
import psp.user.payload.request.LoginRequest;
import psp.user.payload.request.SignUpRequest;
import psp.user.payload.response.JwtResponse;
import psp.user.payload.response.MessageResponse;
import psp.user.security.jwt.JwtUtils;
import psp.user.service.UserDetailsImpl;
import psp.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getUsername(), userDetails.getEmail(), userDetails.getId(), roles)
        );
    }

    @PostMapping("signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.saveUser(new User(null, null, signUpRequest.getUsername(), signUpRequest.getPassword(),
                signUpRequest.getPasswordConfirm(), signUpRequest.getEmail(), signUpRequest.getPhone(),
                signUpRequest.getGender(), signUpRequest.getFirstname(), signUpRequest.getLastname())
        );

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
