package ru.skypro.lessons.springboot.weblibrary.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Long id;
    private String username;
    private String password;
    private int enabled;
    private Role role;



    public static AppUserDTO fromAppUser(AppUser appUser) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(appUser.getId());
        appUserDTO.setUsername(appUser.getUsername());
        appUserDTO.setPassword(appUser.getPassword());
//        appUserDTO.setEnabled(appUser.getEnabled());
        appUserDTO.setRole(appUser.getRole());
        return appUserDTO;
    }

    public AppUser toAppUser() {
        AppUser appUser = new AppUser();
        appUser.setId(this.getId());
        appUser.setUsername(this.getUsername());
        appUser.setPassword(this.getPassword());
//        appUser.setEnabled(this.getEnabled());
        appUser.setRole(this.getRole());
        return appUser;
    }
}
