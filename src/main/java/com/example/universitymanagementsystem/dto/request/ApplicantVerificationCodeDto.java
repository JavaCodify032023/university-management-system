package com.example.universitymanagementsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicantVerificationCodeDto {
    private Long applicantApplicationId;
    @NotNull
    private String code;
}
