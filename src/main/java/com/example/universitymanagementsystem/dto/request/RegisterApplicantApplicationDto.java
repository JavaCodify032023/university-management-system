package com.example.universitymanagementsystem.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class RegisterApplicantApplicationDto {
    private Integer testScore;
    private Long specialtyAdmissionId;
    private Long specialtyId;
    private Long facultyId;
    private Long departmentId;
    private Long personalNumber;
    private String email;
    private String passportId;
    private String country;
    private LocalDate dateOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
}
