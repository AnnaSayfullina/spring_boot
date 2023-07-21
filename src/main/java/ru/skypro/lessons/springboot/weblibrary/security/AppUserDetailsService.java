package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsService implements UserDetailsService {


    private final AppUserRepository appUserRepository;
    private final AppUserDetails appUserDetails;

    public AppUserDetailsService(AppUserRepository appUserRepository, AppUserDetails appUserDetails) {
        this.appUserRepository = appUserRepository;
        this.appUserDetails = appUserDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDTO userDTO = AppUserDTO.fromAppUser(appUserRepository.findAppUserByUsername(username));
        if (userDTO == null) {
            throw new UsernameNotFoundException(username);
        }

        appUserDetails.setUserDetails(userDTO);
        return appUserDetails;
    }
}
