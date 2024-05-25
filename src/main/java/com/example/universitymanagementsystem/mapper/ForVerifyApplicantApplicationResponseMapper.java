package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.response.ForVerifyApplicantApplicationResponseDto;
import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ForVerifyApplicantApplicationResponseMapper {
    @Mappings({
            @Mapping(target = "specialtyName",source = "specialty.name"),
            @Mapping(target = "facultyName",source = "faculty.name"),
            @Mapping(target = "departmentName",source = "department.name"),
            @Mapping(target = "applicantApplicationId", source = "id")
    })
    ForVerifyApplicantApplicationResponseDto entityToDto(ApplicantApplication entity);

    List<ForVerifyApplicantApplicationResponseDto> listEntityToDto(List<ApplicantApplication> entity);
}
