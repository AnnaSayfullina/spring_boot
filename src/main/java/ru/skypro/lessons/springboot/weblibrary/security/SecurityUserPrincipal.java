package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.weblibrary.dto.AuthUserDTO;

import java.util.*;

@Component
public class SecurityUserPrincipal implements UserDetails {

    private AuthUserDTO authUserDTO;

    public SecurityUserPrincipal() {
    }

    public SecurityUserPrincipal(AuthUserDTO authUserDTO) {
        this.authUserDTO = authUserDTO;
    }

    public void setUserDetails(AuthUserDTO authUserDTO) {
        this.authUserDTO = authUserDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(authUserDTO)
                .map(AuthUserDTO::getRole)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .map(List::of)
                .orElse(Collections.emptyList());
    }

    @Override
    public String getPassword() {
        return authUserDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return authUserDTO.getUsername();
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
        if (authUserDTO.getEnabled() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
