package com.example.universitymanagementsystem.entity;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.entity.uni_struct.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "personal_data_id")
    private PersonData personData;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantApplication application;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;

    @Column(name = "expulsion_date")
    private LocalDateTime expulsionDate;

    @Column(name = "is_studying")
    private Boolean isStudying;
    @Column(name = "is_expelled")
    private Boolean isExpelled;
    @Column(name = "test_score")
    private  Integer testScore;


}
