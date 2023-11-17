package psp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import psp.user.dto.UserDto;
import psp.user.exception.UpdateValidationException;
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
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UniqueConstraintViolationException("email", "Email address '" + user.getEmail() + "' already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UniqueConstraintViolationException("username", "Username '" + user.getUsername() + "' already exists");
        }
        if (userRepository.existsByPhone(user.getPhone().trim().replaceAll("\\s", ""))) {
            throw new UniqueConstraintViolationException("phone", "Phone number '" + user.getPhone() + "' already exists");
        }

        if (!user.getPassword().equals(user.getPasswordConfirm()))
            throw new PasswordNotMatchingException();

        final Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        role.ifPresent((value) -> {
            final Set<Role> roles = new HashSet<>();
            roles.add(value);
            user.setRoles(roles);
        });

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
        final UserNotFoundException exception = new UserNotFoundException();
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

    public User updateUser(User user, UserDto dto) {
        if (userRepository.existsByEmailAndNotExcludedUserId(user.getEmail(), user.getId())) {
            throw new UniqueConstraintViolationException("email", "Email address '" + user.getEmail() + "' already exists.");
        }
        if (userRepository.existsByUsernameAndNotExcludedUserId(user.getUsername(), user.getId())) {
            throw new UniqueConstraintViolationException("username", "User name '" + user.getUsername() + "' already exists.");
        }
        if (userRepository.existsByPhoneAndNotExcludedUserId(user.getPhone().trim().replaceAll("\\s", ""), user.getId())) {
            throw new UniqueConstraintViolationException(
                    "phone",
                    "Phone number '" + user.getPhone() + "' already exists."
            );
        }

        if (dto.getPassword() != null) {
            if (dto.getPasswordConfirm() == null) {
                throw new UpdateValidationException("passwordConfirm", "Password confirmation is mandatory.");
            }

            if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
                throw new PasswordNotMatchingException();
            }

            if (!dto.getPassword().matches("^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")) {
                throw new UpdateValidationException("password", "Invalid password. The password must contains at least 8 characters, "
                        + "including one letter, one digit, and one special character (@#$%^&+=!).");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }
}
