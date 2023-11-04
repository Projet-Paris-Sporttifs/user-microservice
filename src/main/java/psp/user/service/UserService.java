package psp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import psp.user.Pagination;
import psp.user.UniqueConstraintViolationException;
import psp.user.UserNotFoundException;
import psp.user.model.User;
import psp.user.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) throws UniqueConstraintViolationException {
        UniqueConstraintViolationException exception = new UniqueConstraintViolationException();
        if (!userRepository.findByEmail(user.getEmail()).isEmpty())
            exception.addError("email", "Email address '" + user.getEmail() + "' already exists");
        if (!userRepository.findByUsername(user.getUsername()).isEmpty())
            exception.addError("username", "Username '" + user.getUsername() + "' already exists");
        if (!userRepository.findByPhone(user.getPhone().trim().replaceAll("\\s", "")).isEmpty())
            exception.addError("phone", "Phone number '" + user.getPhone() + "' already exists");

        if (!exception.isEmpty())
            throw exception;

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            return null;
        }
    }

    public Pagination<User> getPaginatedData(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);

        Pagination<User> pagination = new Pagination<>();
        pagination.setContent(page.get());
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.get().count());
        pagination.setHasNext(page.hasNext());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }

    public User findUserById(String id) {
        UserNotFoundException exception = new UserNotFoundException();
        exception.setFieldName("Id");
        exception.setFieldValue(id);

        try {
            Optional<User> user = userRepository.findById(Long.parseLong(id));
            if (user.isEmpty()) throw exception;
            return user.get();
        } catch (NumberFormatException e) {
            throw exception;
        }
    }
}
