package com.example.universitymanagementsystem.dto.request;

import lombok.Data;

@Data
public class ApplicantApplicationVerifyRequestDto {
    private Long applicantApplicationId;
    private String message;
    
    private boolean verify;
}
