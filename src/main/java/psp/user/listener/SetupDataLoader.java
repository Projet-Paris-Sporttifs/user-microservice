package psp.user.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import psp.user.model.*;
import psp.user.repository.PermissionRepository;
import psp.user.repository.RoleRepository;
import psp.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        // create all permissions
        Arrays.stream(EPermission.values()).forEach(this::createPermissionIfNotFound);

        Set<EPermission> adminsPermissions = Set.of(EPermission.ADD_ADMIN, EPermission.DELETE_ADMIN, EPermission.EDIT_ADMIN);
        Set<EPermission> usersPermissions = Set.of(EPermission.ADD_USER, EPermission.DELETE_USER, EPermission.EDIT_USER);
        Set<EPermission> allPermissions = new HashSet<>(adminsPermissions);
        allPermissions.addAll(usersPermissions);
        Set<EPermission> actionsPermissions = Set.of(EPermission.CREATE_BET);

        // create roles
        createRoleIfNotFound(ERole.ROLE_SUPERADMIN, allPermissions);
        createRoleIfNotFound(ERole.ROLE_ADMIN, usersPermissions);
        createRoleIfNotFound(ERole.ROLE_USER, actionsPermissions);

        // create test accounts
        createAdminAccount();
        createUserAccount();
        createSuperAdminAccount();

        alreadySetup = true;
    }

    @Transactional
    private void createPermissionIfNotFound(EPermission name) {
        Optional<Permission> permission = permissionRepository.findByName(name);

        if (permission.isEmpty()) {
            Permission _permission = new Permission(name);
            permissionRepository.save(_permission);
        }
    }

    @Transactional
    private void createRoleIfNotFound(ERole name, Set<EPermission> permissions) {
        Optional<Role> role = roleRepository.findByName(name);

        if (role.isEmpty()) {
            Set<Permission> _permissions = permissions.stream().map(
                    value -> permissionRepository.findByName(value).get()
            ).collect(Collectors.toSet());

            Role _role = new Role(name);
            _role.setPermissions(_permissions);
            roleRepository.save(_role);
        }
    }

    @Transactional
    private void createAdminAccount() {
        Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        User user = new User(
                null, Stream.of(roleAdmin).collect(Collectors.toSet()),
                "test_admin", passwordEncoder.encode("test"), null, "test_admin@test.com",
                "767555412", "male", "Aly", "Diop", null, null
        );
        userRepository.save(user);
    }

    @Transactional
    private void createSuperAdminAccount() {
        Role roleAdmin = roleRepository.findByName(ERole.ROLE_SUPERADMIN).get();
        User user = new User(
                null, Stream.of(roleAdmin).collect(Collectors.toSet()),
                "test_super_admin", passwordEncoder.encode("test"), null,
                "test_super_admin@test.com", "763555412", "male", "Youssou", "Fall",
                null, null
        );
        userRepository.save(user);
    }

    @Transactional
    private void createUserAccount() {
        Role roleAdmin = roleRepository.findByName(ERole.ROLE_USER).get();
        User user = new User(
                null, Stream.of(roleAdmin).collect(Collectors.toSet()),
                "test_user", passwordEncoder.encode("test"), null, "test_user@test.com",
                "761555412", "male", "Mohamed", "Sy", null, null
        );
        userRepository.save(user);
    }
}
