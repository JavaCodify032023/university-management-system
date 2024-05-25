package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.request.RegisterApplicantApplicationDto;
import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class},componentModel = "spring")
public interface RegisterApplicantApplicationMapper {
    @Mappings({
            @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "specialty.id", source = "specialtyId"),
            @Mapping(target = "faculty.id", source = "facultyId"),
            @Mapping(target = "department.id", source = "departmentId"),
            @Mapping(target = "specialtyAdmission.id", source = "specialtyAdmissionId"),
            @Mapping(target = "isEmailActivated", constant = "false"),
            @Mapping(target = "isChecked", constant = "false")
    })
    ApplicantApplication dtoToEntity(RegisterApplicantApplicationDto dto);
}

