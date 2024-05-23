package com.example.universitymanagementsystem.controller;

import com.example.universitymanagementsystem.dto.request.CreateAdmissionRequestDto;
import com.example.universitymanagementsystem.dto.response.CommonResponseDto;
import com.example.universitymanagementsystem.dto.response.FacultyAdmissionResponseDto;
import com.example.universitymanagementsystem.dto.response.SpecialtyAdmissionResponseDto;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.mapper.FacultyAdmissionResponseMapper;
import com.example.universitymanagementsystem.mapper.SpecialtyAdmissionResponseMapper;
import com.example.universitymanagementsystem.service.SpecialtyAdmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/specialty-admission")
@RequiredArgsConstructor
@Tag(name = "Specialty admission", description = "APIs for specialty admission")
public class AdmissionController {

    private final SpecialtyAdmissionService specialtyAdmissionService;

    private final SpecialtyAdmissionResponseMapper specialtyAdmissionResponseMapper;
    private final FacultyAdmissionResponseMapper facultyAdmissionResponseMapper;

    @Operation(summary = "Get faculty admission",description = "Get faculties where specialty admission is available")
    @GetMapping("/get-faculties-admission")
    public CommonResponseDto<Set<FacultyAdmissionResponseDto>> getFacultiesAdmissions(){
        return new CommonResponseDto<Set<FacultyAdmissionResponseDto>>()
                .setOk()
                .setData(
                        new HashSet<>(facultyAdmissionResponseMapper.listEntityToDto(
                                specialtyAdmissionService.getActiveAdmissions()))
                );
    }

    @Operation(summary = "Get specialty admission",description = "Get specialties by faculty id where admission is available")
    @GetMapping("/get-specialty-admission/{facultyId}")
    public CommonResponseDto<List<SpecialtyAdmissionResponseDto>> getSpecialtyAdmission(
            @PathVariable Long facultyId
    ){
        return new CommonResponseDto<List<SpecialtyAdmissionResponseDto>>()
                .setOk()
                .setData(
                        specialtyAdmissionResponseMapper.listEntityToDto(
                                specialtyAdmissionService.getActiveAdmissions(facultyId))
                );
    }

    @Operation(summary = "Create new admission")
    @PostMapping("/create-new-admission")
    public CommonResponseDto<List<SpecialtyAdmissionResponseDto>> createNewAdmission(
            @RequestBody CreateAdmissionRequestDto createAdmissionRequestDto
            ){
        CommonResponseDto<List<SpecialtyAdmissionResponseDto>> commonResponseDto = new CommonResponseDto<>();
        try {
            commonResponseDto.setOk()
                    .setMessage("Новый набор объявлен")
                    .setData(specialtyAdmissionResponseMapper.listEntityToDto(
                            specialtyAdmissionService.createNewAdmission(
                                    specialtyAdmissionResponseMapper.dtoToEntity(createAdmissionRequestDto))));
        } catch (BaseBusinessLogicException e){
            commonResponseDto.setStatus(400);
            throw new BaseBusinessLogicException(e.getMessage());
        }
        return commonResponseDto;
    }
}
