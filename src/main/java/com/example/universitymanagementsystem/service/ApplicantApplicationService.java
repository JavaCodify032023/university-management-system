package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;

import java.util.List;

public interface ApplicantApplicationService {
    Long registerApplicantApplication(ApplicantApplication app);
    Long saveApp(ApplicantApplication applicantApplication);
    List<ApplicantApplication> getEmailVerifiedApplicants();
    List<ApplicantApplication> getByAdmissionId(Long admissionId);
}
