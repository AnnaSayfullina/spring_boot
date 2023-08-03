package ru.skypro.lessons.springboot.weblibrary.security;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "app_user")
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public AppUser() {
    }

    public AppUser(String username, String password, Role role) {
        this.username = correctUsername(username);
        this.password = password;
        this.role = role;
    }

    public String correctUsername(String username){
        return StringUtils.capitalize(username.toLowerCase());
    }

}
