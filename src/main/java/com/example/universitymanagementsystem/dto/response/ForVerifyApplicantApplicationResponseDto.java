package com.example.universitymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Tag(name = "Applicant application",description = "Applicant application where email verified for commission admission to verify application data")
@Data
public class ForVerifyApplicantApplicationResponseDto {

    private Long applicantApplicationId;
    private LocalDateTime createdDate;
    private Integer testScore;
    private String specialtyName;
    private String facultyName;
    private String departmentName;
    private Long personalNumber;
    private String email;
    private String nationality;
    private String passportId;
    private String country;
    private String city;
    private LocalDate dateOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;

}
