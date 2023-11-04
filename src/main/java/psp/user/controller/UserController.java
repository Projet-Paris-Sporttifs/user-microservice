package psp.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psp.user.Pagination;
import psp.user.UniqueConstraintViolationException;
import psp.user.UserNotFoundException;
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
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) throws UniqueConstraintViolationException {
        User _user = userService.saveUser(user);
        return new ResponseEntity<>(_user, _user != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public Pagination<User> getUsers(@RequestParam(required = false) String page, @RequestParam(required = false) String limit) {
        if (page == null) page = "0";
        if (limit == null) limit = props.getLimit();

        Pageable pageable = PageRequest.of(
                page.matches("\\d+") ? Integer.parseInt(page) : 0,
                Integer.parseInt(limit.matches("\\d+") ? limit : props.getLimit())
        );

        return userService.getPaginatedData(pageable);
    }
}
