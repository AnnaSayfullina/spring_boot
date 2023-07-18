package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.security.core.GrantedAuthority;

public class SecurityGrantedAthorities implements GrantedAuthority {
    private String role;


    public SecurityGrantedAthorities(Authority authority) {
        this.role = authority.getRole();


    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
