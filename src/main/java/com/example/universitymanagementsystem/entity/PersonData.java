package com.example.universitymanagementsystem.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "person_data")
@Data
public class PersonData extends BaseEntity {
    @Column(unique = true,nullable = false)
    private Long personalNumber;

    private String email;

    private String nationality;

    @Column(name = "passport_id",nullable = false)
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
}
