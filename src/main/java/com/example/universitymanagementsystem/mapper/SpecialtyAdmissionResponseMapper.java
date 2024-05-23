package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.request.CreateAdmissionRequestDto;
import com.example.universitymanagementsystem.dto.response.SpecialtyAdmissionResponseDto;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.entity.uni_struct.Department;
import com.example.universitymanagementsystem.entity.uni_struct.Faculty;
import com.example.universitymanagementsystem.entity.uni_struct.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyAdmissionResponseMapper {
    @Mappings({
            @Mapping(target = "admissionId",source = "id"),
            @Mapping(target = "specialtyId",source = "specialty.id"),
            @Mapping(target = "specialtyName",source = "specialty.name"),
            @Mapping(target = "groupCapacity",expression = "java(specialtyAdmission.getGroupAmount() * specialtyAdmission.getGroupCapacity())")
    })
    SpecialtyAdmissionResponseDto entityToDto(SpecialtyAdmission specialtyAdmission);

    List<SpecialtyAdmissionResponseDto> listEntityToDto(List<SpecialtyAdmission> specialtyAdmissionList);

    default SpecialtyAdmission dtoToEntity(CreateAdmissionRequestDto createAdmissionRequestDto) {
        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();

        Specialty specialty = new Specialty();
        specialty.setId(createAdmissionRequestDto.getSpecialtyId());

        Faculty faculty = new Faculty();
        faculty.setId(createAdmissionRequestDto.getFacultyId());

        Department department = new Department();
        department.setId(createAdmissionRequestDto.getDepartmentId());

        specialtyAdmission.setSpecialty(specialty);
        specialtyAdmission.setFaculty(faculty);
        specialtyAdmission.setDepartment(department);
        specialtyAdmission.setGroupAmount(createAdmissionRequestDto.getGroupAmount());
        specialtyAdmission.setGroupCapacity(createAdmissionRequestDto.getGroupCapacity());
        specialtyAdmission.setMinScore(createAdmissionRequestDto.getMinScore());
        specialtyAdmission.setStartDate(createAdmissionRequestDto.getStartDate().atStartOfDay());
        specialtyAdmission.setEndDate(createAdmissionRequestDto.getEndDate().atStartOfDay());

        return specialtyAdmission;
    }
}
