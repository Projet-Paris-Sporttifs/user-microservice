package psp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import psp.user.dto.UserDto;
import psp.user.dto.mapper.UserMapper;
import psp.user.model.User;
import psp.user.payload.response.MessageResponse;
import psp.user.service.UserDetailsImpl;
import psp.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper mapper;

    @PutMapping
    public ResponseEntity<User> update(@RequestBody final UserDto dto) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final User user = userService.findUserById(userDetails.getId());

        mapper.updateUserFromDto(dto, user);
        return ResponseEntity.ok(userService.updateUser(user, dto));
    }

    @DeleteMapping
    public MessageResponse delete() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        userService.removeUser(userDetails.getId());

        return new MessageResponse("Account closed successfully.");
    }
}
