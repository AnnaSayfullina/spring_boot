package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.Data;
import ru.skypro.lessons.springboot.weblibrary.security.AuthUser;
import ru.skypro.lessons.springboot.weblibrary.security.Role;

@Data
public class AuthUserDTO {

    private int id;
    private String username;
    private String password;
    private int enabled;
    private Role role;

    public static AuthUserDTO fromAuthUser(AuthUser authUser) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setId(authUser.getId());
        authUserDTO.setUsername(authUser.getUsername());
        authUserDTO.setPassword(authUser.getPassword());
        authUserDTO.setEnabled(authUser.getEnabled());
        authUserDTO.setRole(authUser.getRole());
        return authUserDTO;
    }

    public AuthUser toAuthUser() {
        AuthUser authUser = new AuthUser();
        authUser.setId(this.getId());
        authUser.setUsername(this.getUsername());
        authUser.setPassword(this.getPassword());
        authUser.setEnabled(this.getEnabled());
        authUser.setRole(this.getRole());
        return authUser;
    }
}
