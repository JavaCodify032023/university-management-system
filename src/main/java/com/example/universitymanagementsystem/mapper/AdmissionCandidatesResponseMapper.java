package com.example.universitymanagementsystem.mapper;

import com.example.universitymanagementsystem.dto.response.AdmissionCandidatesResponseDto;
import com.example.universitymanagementsystem.entity.applyment.Candidate;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface AdmissionCandidatesResponseMapper {

    @Mappings({
            @Mapping(target = "firstName", source = "candidate.applicantApplication.firstName"),
            @Mapping(target = "middleName", source = "candidate.applicantApplication.middleName"),
            @Mapping(target = "lastName", source = "candidate.applicantApplication.lastName")
    })
    AdmissionCandidatesResponseDto entityToDto(Candidate candidate);

    List<AdmissionCandidatesResponseDto> listEntityToDto(List<Candidate> candidateList);
}
