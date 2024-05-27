package com.example.universitymanagementsystem.entity.applyment;

import com.example.universitymanagementsystem.entity.BaseEntity;
import com.example.universitymanagementsystem.entity.uni_struct.Department;
import com.example.universitymanagementsystem.entity.uni_struct.Faculty;
import com.example.universitymanagementsystem.entity.uni_struct.Specialty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "specialty_admission")
@Data
public class SpecialtyAdmission extends BaseEntity {
    @Column(name = "start_date",nullable = false)
    private LocalDateTime startDate;

    @Column(name ="end_date",nullable = false)
    private LocalDateTime endDate;

    @Column(name = "group_capacity",nullable = false)
    private Integer groupCapacity;

    @Column(name = "group_amount",nullable = false)
    private Integer groupAmount;

    @Column(name = "min_score")
    private Integer minScore;

    @ManyToOne
    @JoinColumn(name = "specialty_id",nullable = false)
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "faculty_id",nullable = false)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "department_id",nullable = false)
    private Department department;

}
