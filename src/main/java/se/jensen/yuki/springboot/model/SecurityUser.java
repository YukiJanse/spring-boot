package se.jensen.yuki.springboot.model;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {
    private final Long id;
    private final String email;
    private final String role;
    private final String password;

    public SecurityUser(UserJpaEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.password = user.getPassword();
    }

    public SecurityUser(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = null;
    }

    public Long getId() {
        return id;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String r = role;
        String role = r == null || r.isBlank() ? "ROLE_USER" :
                (r.startsWith("ROLE_") ? r : "ROLE_" + r);
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
