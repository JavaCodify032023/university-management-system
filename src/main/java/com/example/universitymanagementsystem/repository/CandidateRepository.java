package com.example.universitymanagementsystem.repository;

import com.example.universitymanagementsystem.entity.applyment.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate,Long> {

    @Query("select c from candidate c " +
            "where c.specialtyAdmission.id = :specialtyAdmissionId " +
            "and local_datetime between c.specialtyAdmission.startDate and c.specialtyAdmission.endDate")
    List<Candidate> findAllActiveBySpecId(Long specialtyAdmissionId);


    @Query("SELECT c FROM candidate c " +
            "where local_datetime between c.specialtyAdmission.startDate and c.specialtyAdmission.endDate " +
            "and c.specialtyAdmission.id = :admissionId")
    List<Candidate> findAllActiveByAdmissionId(Long admissionId);

    @Query("SELECT c FROM candidate c " +
            "where c.specialtyAdmission.id = :admissionId")
    List<Candidate> findAllByAdmissionId(Long admissionId);

    @Query("SELECT c FROM candidate c " +
            "where local_datetime between c.specialtyAdmission.startDate and c.specialtyAdmission.endDate " +
            "and c.applicantApplication.personalNumber = :personalNumber")
    Optional<Candidate> findActiveByPn(Long personalNumber);
}
