package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.response.FacultyAdmissionResponseDto;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyAdmissionResponseMapper {
    @Mappings({
            @Mapping(target = "facultyName",source = "faculty.name"),
            @Mapping(target = "facultyId",source = "faculty.id")
    })
    FacultyAdmissionResponseDto entityToDto(SpecialtyAdmission specialtyAdmission);

    List<FacultyAdmissionResponseDto> listEntityToDto(List<SpecialtyAdmission> specialtyAdmissionList);
}
