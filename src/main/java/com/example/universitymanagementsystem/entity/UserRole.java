package com.example.universitymanagementsystem.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
@Entity(name = "user_role")
@Data
public class UserRole extends BaseEntity implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
