package psp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import psp.user.model.ERole;
import psp.user.model.Role;
import psp.user.payload.response.PaginationResponse;
import psp.user.exception.PasswordNotMatchingException;
import psp.user.exception.UniqueConstraintViolationException;
import psp.user.exception.UserNotFoundException;
import psp.user.model.User;
import psp.user.repository.RoleRepository;
import psp.user.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) throws UniqueConstraintViolationException, PasswordNotMatchingException {
        final UniqueConstraintViolationException exception = new UniqueConstraintViolationException();
        userRepository.findByEmail(user.getEmail()).ifPresent(
                (value) -> exception.addError("email", "Email address '" + user.getEmail() + "' already exists")
        );
        userRepository.findByUsername(user.getUsername()).ifPresent(
                (value) -> exception.addError("username", "Username '" + user.getUsername() + "' already exists")
        );
        userRepository.findByPhone(user.getPhone().trim().replaceAll("\\s", "")).ifPresent(
                (value) -> exception.addError("phone", "Phone number '" + user.getPhone() + "' already exists")
        );

        if (!exception.isEmpty())
            throw exception;

        if (!user.getPassword().equals(user.getPasswordConfirm()))
            throw new PasswordNotMatchingException();

        final Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        final Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            return null;
        }
    }

    public PaginationResponse<User> getPaginatedData(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);

        PaginationResponse<User> pagination = new PaginationResponse<>();
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
