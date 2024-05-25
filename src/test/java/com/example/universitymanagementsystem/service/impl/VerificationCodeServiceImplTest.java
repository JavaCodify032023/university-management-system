package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.entity.applyment.VerificationCode;
import com.example.universitymanagementsystem.repository.ApplicantApplicationRepository;
import com.example.universitymanagementsystem.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class VerificationCodeServiceImplTest {
    @InjectMocks
    private VerificationCodeServiceImpl verificationCodeService;
    @Mock
    private VerificationCodeRepository verificationCodeRepository;
    @Mock
    private ApplicantApplicationRepository applicationRepository;


    @Test
    void verificateApplicantApplication_success() {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode("Dda-42d");
        VerificationCode currenVerCode = new VerificationCode();
        currenVerCode.setCode("Dda-42d");
        currenVerCode.setExpireDate(now().plusMinutes(5));
        when(verificationCodeRepository.getByApplicantApplicationId(1L)).thenReturn(Optional.of(currenVerCode));

        verificationCode.setApplicantApplicationId(1L);
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(new ApplicantApplication()));

        Boolean result = verificationCodeService.verificateApplicantApplication(verificationCode);
        assertEquals(true,result);
    }

    @Test
    void verificateApplicantApplication_incorrect_code(){
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setApplicantApplicationId(1L);
        verificationCode.setCode("Afr");

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                verificationCodeService.verificateApplicantApplication(verificationCode));
        assertEquals("Неверный код подтверждения. Должно быть 7 символов. Пример: xxx-xxx",baseBusinessLogicException.getMessage());
    }

    @Test
    void verificateApplicantApplication_invalid_code() {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setApplicantApplicationId(1L);
        verificationCode.setCode("Afr-d4h");

        VerificationCode currentVerificationCode = new VerificationCode();
        currentVerificationCode.setApplicantApplicationId(1L);
        currentVerificationCode.setCode("Afr-d4h");

        when(verificationCodeRepository.getByApplicantApplicationId(anyLong())).thenReturn(Optional.empty());

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                verificationCodeService.verificateApplicantApplication(verificationCode));
        assertEquals("Неверный код подтверждения. Повторите еще раз!",baseBusinessLogicException.getMessage());

    }

    @Test
    void verificateApplicantApplication_expired_code(){
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setApplicantApplicationId(1L);
        verificationCode.setCode("Afr-d4h");

        VerificationCode currentVerificationCode = new VerificationCode();
        currentVerificationCode.setApplicantApplicationId(1L);
        currentVerificationCode.setCode("Afr-d4h");
        currentVerificationCode.setExpireDate(now().minusMinutes(5));

        when(verificationCodeRepository.getByApplicantApplicationId(anyLong())).thenReturn(Optional.of(currentVerificationCode));

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                verificationCodeService.verificateApplicantApplication(verificationCode));
        assertEquals("Код подтверждения истек. Запросите новый",baseBusinessLogicException.getMessage());
    }

    @Test
    void verificateApplicantApplication_applicant_not_found(){
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setApplicantApplicationId(1L);
        verificationCode.setCode("Afr-d4h");

        VerificationCode currentVerificationCode = new VerificationCode();
        currentVerificationCode.setApplicantApplicationId(1L);
        currentVerificationCode.setCode("Afr-d4h");
        currentVerificationCode.setExpireDate(now().plusMinutes(5));

        when(verificationCodeRepository.getByApplicantApplicationId(anyLong())).thenReturn(Optional.of(currentVerificationCode));
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                verificationCodeService.verificateApplicantApplication(verificationCode));
        assertEquals("Ошибка подтверждения почты. Попробуйте еще раз.",baseBusinessLogicException.getMessage());
    }
}
