package ru.skypro.lessons.springboot.weblibrary.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column (nullable = false)
    private String password;

    private int enabled;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Role role;

//    private boolean isEnabled;
//    @JoinColumn (name = "user_id")
//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Authority> authorityList;


}
