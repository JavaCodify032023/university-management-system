package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.dto.request.LoginRequestDto;
import com.example.universitymanagementsystem.dto.response.TokenResponseDto;

public interface AuthService {
    TokenResponseDto authenticateUser(LoginRequestDto cred);
}
