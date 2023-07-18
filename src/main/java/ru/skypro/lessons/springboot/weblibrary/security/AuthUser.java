package ru.skypro.lessons.springboot.weblibrary.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

//    private boolean isEnabled;
    @JoinColumn (name = "user_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Authority> authorityList;


}
