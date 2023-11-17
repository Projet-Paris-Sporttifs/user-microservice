package psp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import psp.user.dto.UserDto;
import psp.user.dto.mapper.UserMapper;
import psp.user.exception.PaginationParamsException;
import psp.user.payload.response.PaginationResponse;
import psp.user.exception.UserNotFoundException;
import psp.user.model.User;
import psp.user.repository.CustomProperties;
import psp.user.repository.UserRepository;
import psp.user.service.UserDetailsImpl;
import psp.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomProperties props;

    @Autowired
    UserMapper mapper;

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping("{id:[\\d]+}")
    public ResponseEntity<User> getUser(@PathVariable final String id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping
    public PaginationResponse<User> getUsers(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String limit
    ) {
        if (null == page) page = "0";
        if (null == limit) limit = props.getPaginationLimit();

        try {
            Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit));
            return userService.getPaginatedData(pageable);
        } catch (NumberFormatException e) {
            throw new PaginationParamsException();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUSer(@RequestBody final UserDto dto) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final Optional<User> user = userRepository.findById(userDetails.getId());

        if (user.isPresent()) {
            mapper.updateUserFromDto(dto, user.get());
            return ResponseEntity.ok(userService.updateUser(user.get(), dto));
        }

        throw new UserNotFoundException("Id", userDetails.getId().toString());
    }
}
