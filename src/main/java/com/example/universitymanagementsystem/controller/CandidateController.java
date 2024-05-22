package com.example.universitymanagementsystem.controller;

import com.example.universitymanagementsystem.dto.response.AdmissionCandidatesResponseDto;
import com.example.universitymanagementsystem.dto.response.CommonResponseDto;
import com.example.universitymanagementsystem.mapper.AdmissionCandidatesResponseMapper;
import com.example.universitymanagementsystem.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
@Tag(name = "Candidate", description = "APIs for candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final AdmissionCandidatesResponseMapper admissionCandidatesResponseMapper;

    @Operation(description = "Get candidates to the specialty by admission id.")
    @GetMapping("/get-all")
    @PermitAll
    public CommonResponseDto<List<AdmissionCandidatesResponseDto>> getCandidates(
            @RequestParam Long admissionId
    ){
        return new CommonResponseDto<List<AdmissionCandidatesResponseDto>>()
                .setOk()
                .setData(
                        admissionCandidatesResponseMapper.listEntityToDto(
                                candidateService.getAllActiveByAdmissionId(admissionId))
                );
    }
}
