package psp.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psp.user.payload.request.SignUpRequest;
import psp.user.payload.response.MessageResponse;
import psp.user.payload.response.PaginationResponse;
import psp.user.exception.PasswordNotMatchingException;
import psp.user.exception.UniqueConstraintViolationException;
import psp.user.exception.UserNotFoundException;
import psp.user.model.User;
import psp.user.repository.CustomProperties;
import psp.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CustomProperties props;

    @PostMapping()
    public ResponseEntity<MessageResponse> addUser(@RequestBody @Valid SignUpRequest signUpRequest)
            throws UniqueConstraintViolationException, PasswordNotMatchingException {
        User user = userService.saveUser(new User(null, null, signUpRequest.getUsername(), signUpRequest.getPassword(),
                signUpRequest.getPasswordConfirm(), signUpRequest.getEmail(), signUpRequest.getPhone(),
                signUpRequest.getGender(), signUpRequest.getFirstname(), signUpRequest.getLastname()));

        ResponseEntity<MessageResponse> response;

        if (user != null)
            response = new ResponseEntity<>(new MessageResponse("User created successfully."), HttpStatus.CREATED);
        else
            response = new ResponseEntity<>(new MessageResponse("An error has occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

    @GetMapping("{id:[\\d]+}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping()
    public PaginationResponse<User> getUsers(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String limit
    ) {
        if (page == null) page = "0";
        if (limit == null) limit = props.getPaginationLimit();

        Pageable pageable = PageRequest.of(
                page.matches("\\d+") ? Integer.parseInt(page) : 0,
                Integer.parseInt(limit.matches("\\d+") ? limit : props.getPaginationLimit())
        );

        return userService.getPaginatedData(pageable);
    }
}
