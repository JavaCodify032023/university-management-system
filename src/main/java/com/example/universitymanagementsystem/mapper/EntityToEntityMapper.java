package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.entity.PersonData;
import com.example.universitymanagementsystem.entity.Student;
import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.service.PersonDataService;
import com.example.universitymanagementsystem.service.StudentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EntityToEntityMapper {
    PersonData applicantApplicationToPersonData(ApplicantApplication applicantApplication);
    @Mappings({
            @Mapping(target = "personData.id",source = "id"),
            @Mapping(target = "",source = ),
            @Mapping(target = "",source = ),
    })
    Student personDataToStudent(PersonData personData);
}
