package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Long save(User user);
}
