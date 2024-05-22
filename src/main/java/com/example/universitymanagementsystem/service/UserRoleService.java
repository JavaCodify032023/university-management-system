package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.UserRole;
import com.example.universitymanagementsystem.entity.enums.UserRoleEnum;

public interface UserRoleService {
    UserRole findByName(String name);
    UserRole findById(Long id);
}
