package com.example.universitymanagementsystem.repository;

import com.example.universitymanagementsystem.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(String name);
}
