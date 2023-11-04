package psp.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psp.user.UniqueConstraintViolationException;
import psp.user.UserNotFoundException;
import psp.user.model.User;
import psp.user.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) throws UniqueConstraintViolationException {
        checkUniqueConstraints(user);
        try {
            User _user = userRepository.save(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<User> addUser(@PathVariable String id) throws UserNotFoundException {
        UserNotFoundException exception = new UserNotFoundException();
        exception.setFieldName("Id");
        exception.setFieldValue(id);

        try {
            Optional<User> user = userRepository.findById(Long.parseLong(id));
            if (user.isEmpty()) throw exception;
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw exception;
        }
    }

    private void checkUniqueConstraints(User user) throws UniqueConstraintViolationException {
        UniqueConstraintViolationException exception = new UniqueConstraintViolationException();
        if (!userRepository.findByEmail(user.getEmail()).isEmpty())
            exception.addError("email", "Email address '" + user.getEmail() + "' already exists");
        if (!userRepository.findByUsername(user.getUsername()).isEmpty())
            exception.addError("username", "Username '" + user.getUsername() + "' already exists");
        if (!userRepository.findByPhone(user.getPhone().trim().replaceAll("\\s", "")).isEmpty())
            exception.addError("phone", "Phone number '" + user.getPhone() + "' already exists");

        if (!exception.isEmpty())
            throw exception;
    }
}
