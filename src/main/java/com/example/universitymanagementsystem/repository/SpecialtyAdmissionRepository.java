package com.example.universitymanagementsystem.repository;

import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpecialtyAdmissionRepository extends JpaRepository<SpecialtyAdmission,Long> {
    @Query(value = "select s from specialty_admission s " +
            "where local_datetime between s.startDate and s.endDate " +
            " and s.specialty.id = :specialtyId")
    Optional<SpecialtyAdmission> getActiveBySpecId(Long specialtyId);

    @Query(value = "select s from specialty_admission s " +
            "where local_datetime between s.startDate and s.endDate")
    List<SpecialtyAdmission> getAllActive();

    @Query(value = "select s from specialty_admission s " +
            " where local_datetime between s.startDate and s.endDate" +
            " and s.faculty.id = :facultyId")
    List<SpecialtyAdmission> getAllActive(Long facultyId);

    @Query(value = "select s from specialty_admission s " +
            "where local_datetime between local_datetime and s.endDate " +
            " and s.specialty.id = :specialtyId")
    Optional<List<SpecialtyAdmission>> getActiveBySpecialtyId(Long specialtyId);
}
