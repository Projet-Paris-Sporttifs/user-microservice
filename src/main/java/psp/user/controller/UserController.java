package psp.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<User> addUser(@Valid @RequestBody User user)
            throws UniqueConstraintViolationException, PasswordNotMatchingException {
        User _user = userService.saveUser(user);
        return new ResponseEntity<>(_user, _user != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
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
