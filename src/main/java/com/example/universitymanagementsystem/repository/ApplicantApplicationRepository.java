package com.example.universitymanagementsystem.repository;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicantApplicationRepository extends JpaRepository<ApplicantApplication,Long> {
    @Query("select a from applicant_application a " +
            "where a.isChecked = false " +
            "and a.personalNumber = :personalNumber")
    Optional<ApplicantApplication> findByPnWhichIsNonChecked(Long personalNumber);
    @Query("select a from applicant_application a " +
            "where a.isChecked = false and a.isEmailActivated = true")
    List<ApplicantApplication> getAllNonCheckedActivated();

    @Query("select  a from applicant_application a " +
            "where a.specialtyAdmission.id = :admissionId")
    List<ApplicantApplication> getAllByAdmissionId(Long admissionId);
}
