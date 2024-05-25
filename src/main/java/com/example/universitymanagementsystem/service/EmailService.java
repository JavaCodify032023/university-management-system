package com.example.universitymanagementsystem.service;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMessage(String email, String subject, String message);
}
