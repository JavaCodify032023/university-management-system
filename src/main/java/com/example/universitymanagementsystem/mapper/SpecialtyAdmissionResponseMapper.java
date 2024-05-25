package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.request.CreateAdmissionRequestDto;
import com.example.universitymanagementsystem.dto.response.SpecialtyAdmissionResponseDto;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(imports = {LocalDateTime.class},componentModel = "spring")
public interface SpecialtyAdmissionResponseMapper {
    @Mappings({
            @Mapping(target = "admissionId",source = "id"),
            @Mapping(target = "specialtyId",source = "specialty.id"),
            @Mapping(target = "specialtyName",source = "specialty.name"),
            @Mapping(target = "groupCapacity",expression = "java(specialtyAdmission.getGroupAmount() * specialtyAdmission.getGroupCapacity())")
    })
    SpecialtyAdmissionResponseDto entityToDto(SpecialtyAdmission specialtyAdmission);

    List<SpecialtyAdmissionResponseDto> listEntityToDto(List<SpecialtyAdmission> specialtyAdmissionList);

    @Mappings({
            @Mapping(target = "specialty.id",source = "specialtyId"),
            @Mapping(target = "faculty.id",source = "facultyId"),
            @Mapping(target = "department.id",source = "departmentId"),
            @Mapping(target = "createdDate",expression = "java(LocalDateTime.now())")
    })
    SpecialtyAdmission dtoToEntity(CreateAdmissionRequestDto createAdmissionRequestDto);
}
