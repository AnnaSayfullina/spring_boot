package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUserPrincipal implements UserDetails {

    private String userName;
    private String password;
    private List<SecurityGrantedAthorities> athoritiesList;

    public SecurityUserPrincipal(AuthUser user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.athoritiesList = user.getAuthorityList().stream()
                .map(SecurityGrantedAthorities::new)
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(athoritiesList);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
}
