package psp.user.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import psp.user.model.Permission;
import psp.user.model.Role;
import psp.user.model.User;

import java.util.*;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        Collection<GrantedAuthority> authorities = getGrantedAuthorities(user.getRoles());
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    private static Collection<String> getPermissions(Collection<Role> roles) {
        Collection<String> permissions = new ArrayList<>();
        Collection<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            permissions.add(role.getName().name());
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add(item.getName().name());
        }
        return permissions;
    }

    private static Collection<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        Collection<String> permissions = getPermissions(roles);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : permissions) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
