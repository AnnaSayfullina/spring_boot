package ru.skypro.lessons.springboot.weblibrary.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Integer> {

    AuthUser findByUsername(String username);
}
