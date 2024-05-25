package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.response.SpecialtyAdmissionResponseDto;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyAdmissionResponseMapper {
    @Mappings({
            @Mapping(target = "admissionId",source = "id"),
            @Mapping(target = "specialtyId",source = "specialty.id"),
            @Mapping(target = "departmentId",source = "department.id"),
            @Mapping(target = "specialtyName",source = "specialty.name"),
            @Mapping(target = "groupCapacity",expression = "java(specialtyAdmission.getGroupAmount() * specialtyAdmission.getGroupCapacity())")
    })
    SpecialtyAdmissionResponseDto entityToDto(SpecialtyAdmission specialtyAdmission);

    List<SpecialtyAdmissionResponseDto> listEntityToDto(List<SpecialtyAdmission> specialtyAdmissionList);
}
