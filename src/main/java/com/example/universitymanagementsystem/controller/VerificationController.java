package com.example.universitymanagementsystem.controller;

import com.example.universitymanagementsystem.dto.request.ApplicantVerificationCodeDto;
import com.example.universitymanagementsystem.dto.response.CommonResponseDto;
import com.example.universitymanagementsystem.mapper.ApplicantVerificationCodeMapper;
import com.example.universitymanagementsystem.mapper.RegisterApplicantApplicationMapper;
import com.example.universitymanagementsystem.service.VerificationCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Verification API")
@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationCodeService verificationCodeService;
    private final ApplicantVerificationCodeMapper applicantApplicationMapper;

    @PostMapping("/activate-applicant")
    @PermitAll
    public CommonResponseDto<Void> activate(@RequestBody ApplicantVerificationCodeDto verificationCodeDto){
        verificationCodeService.verificateApplicantApplication(applicantApplicationMapper.dtoToEntity(verificationCodeDto));
        return new CommonResponseDto<Void>().setOk();
    }
}
