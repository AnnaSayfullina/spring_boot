package ru.skypro.lessons.springboot.weblibrary.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private int id;
    private String username;
    private String password;
    private int enabled;
    private Role role;



    public static AppUserDTO fromAppUser(AppUser appUser) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(appUser.getId());
        appUserDTO.setUsername(appUser.getUsername());
        appUserDTO.setPassword(appUser.getPassword());
        appUserDTO.setRole(appUser.getRole());
        return appUserDTO;
    }

    public AppUser toAppUser() {
        AppUser appUser = new AppUser();
        appUser.setId(this.getId());
        appUser.setUsername(this.getUsername());
        appUser.setPassword(this.getPassword());
        appUser.setRole(this.getRole());
        return appUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
