package com.example.universitymanagementsystem.service;

public interface ApplicantVerificationFacade {

    Boolean verifyApplicantApplication(Long applicantApplicationId, String reason, Boolean decision);
}
