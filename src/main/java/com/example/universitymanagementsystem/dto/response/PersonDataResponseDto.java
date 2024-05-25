package com.example.universitymanagementsystem.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonDataResponseDto {
    private Long personalNumber;

    private String email;

    private String nationality;

    private String passportId;

    private String country;

    private String city;

    private LocalDateTime dateOfBirth;

    private String firstName;

    private String middleName;

    private String lastName;
}
