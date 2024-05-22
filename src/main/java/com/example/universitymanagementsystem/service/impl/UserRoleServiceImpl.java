package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.UserRole;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.UserRoleRepository;
import com.example.universitymanagementsystem.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserRole findByName(String name) {
        return userRoleRepository.findByName(name)
                .orElseThrow(() -> new BaseBusinessLogicException("Не удалось найти роль"));
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new BaseBusinessLogicException("Не удалось найти роль"));
    }
}
