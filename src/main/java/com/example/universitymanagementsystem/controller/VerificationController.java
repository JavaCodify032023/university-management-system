package com.example.universitymanagementsystem.controller;

import com.example.universitymanagementsystem.dto.request.ApplicantVerificationCodeDto;
import com.example.universitymanagementsystem.mapper.ApplicantVerificationCodeMapper;
import com.example.universitymanagementsystem.mapper.RegisterApplicantApplicationMapper;
import com.example.universitymanagementsystem.service.VerificationCodeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
public class VerificationController {
    private final VerificationCodeService verificationCodeService;
    private final ApplicantVerificationCodeMapper applicantApplicationMapper;

    public VerificationController(VerificationCodeService verificationCodeService, ApplicantVerificationCodeMapper applicantApplicationMapper) {
        this.verificationCodeService = verificationCodeService;
        this.applicantApplicationMapper = applicantApplicationMapper;
    }

    @PostMapping("/activate-applicant")
    public String activate(@RequestBody ApplicantVerificationCodeDto verificationCodeDto){
        try{
            verificationCodeService.verificateApplicantApplication(applicantApplicationMapper.dtoToEntity(verificationCodeDto));
            return "Ok";
        } catch (Exception ex){
            return "Error";
        }
    }
}
