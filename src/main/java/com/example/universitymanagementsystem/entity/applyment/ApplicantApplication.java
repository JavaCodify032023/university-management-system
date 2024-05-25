package com.example.universitymanagementsystem.entity.applyment;

import com.example.universitymanagementsystem.entity.BaseEntity;
import com.example.universitymanagementsystem.entity.uni_struct.Department;
import com.example.universitymanagementsystem.entity.uni_struct.Faculty;
import com.example.universitymanagementsystem.entity.uni_struct.Specialty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "applicant_application")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ApplicantApplication extends BaseEntity {

    @Column(name = "application_date")
    private LocalDateTime createdDate;

    @Column(name = "test_score")
    private Integer testScore;

    @ManyToOne
    @JoinColumn(name = "specialty_admission_id",nullable = false)
    private SpecialtyAdmission specialtyAdmission;

    @ManyToOne
    @JoinColumn(name = "specialty_id",nullable = false)
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "faculty_id",nullable = false)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "department_id",nullable = false)
    private Department department;

    @Column(name = "personal_number",nullable = false)
    private Long personalNumber;

    @Column(nullable = false)
    private String email;

    private String nationality;

    @Column(name = "passport_id")
    private String passportId;

    private String country;

    private String city;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_email_activated")
    private Boolean isEmailActivated;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(name = "is_declined")
    private Boolean isDeclined;

    @Column(name = "is_checked")
    private Boolean isChecked;
}
