package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);
}
