package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;

import java.util.List;

public interface SpecialtyAdmissionService {
    List<SpecialtyAdmission> getActiveAdmissions();
    List<SpecialtyAdmission> getActiveAdmissions(Long facultyId);
}
