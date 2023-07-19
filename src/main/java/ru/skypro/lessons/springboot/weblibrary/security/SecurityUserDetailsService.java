package ru.skypro.lessons.springboot.weblibrary.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.weblibrary.dto.AuthUserDTO;

@Component
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private SecurityUserPrincipal userPrincipal;

    public SecurityUserDetailsService(UserRepository userRepository, SecurityUserPrincipal userPrincipal) {
        this.userRepository = userRepository;
        this.userPrincipal = userPrincipal;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        AuthUser authUser = userRepository.findByUsername(username);

        if (authUser == null) {
            throw new UsernameNotFoundException(username);
        }
        AuthUserDTO userDTO = AuthUserDTO.fromAuthUser(authUser);
        return new SecurityUserPrincipal(userDTO);
    }

}