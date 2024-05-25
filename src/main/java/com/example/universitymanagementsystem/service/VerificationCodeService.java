package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.entity.applyment.VerificationCode;

public interface VerificationCodeService {
    Boolean verificateApplicantApplication(VerificationCode verificationCode);
    VerificationCode generateCode(Long applicantApplicationId);
}
