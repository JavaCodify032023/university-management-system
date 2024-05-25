package com.example.universitymanagementsystem.repository;

import com.example.universitymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    UserDetails getByLogin(String login);
    @Query("select u from users  u where u.login = :login")
    UserDetails findUserByLogin(String login);
}
