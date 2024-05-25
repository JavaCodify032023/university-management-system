package com.example.universitymanagementsystem.entity.applyment;

import com.example.universitymanagementsystem.entity.BaseEntity;
import com.example.universitymanagementsystem.entity.uni_struct.Department;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "candidate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "applicant_application_id")
    private ApplicantApplication applicantApplication;

    @Column(name = "test_score")
    private Integer testScore;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "specialty_admission_id")
    private SpecialtyAdmission specialtyAdmission;

    @Transient
    private Boolean isRecommended;
}
