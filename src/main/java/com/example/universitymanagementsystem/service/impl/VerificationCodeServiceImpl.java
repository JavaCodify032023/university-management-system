package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.VerificationCode;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.ApplicantApplicationRepository;
import com.example.universitymanagementsystem.repository.VerificationCodeRepository;
import com.example.universitymanagementsystem.service.VerificationCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final ApplicantApplicationRepository applicationRepository;

    @Override
    @Transactional
    public Boolean verificateApplicantApplication(VerificationCode verificationCode){
        if(verificationCode.getCode().length() != 7){
            throw new BaseBusinessLogicException("Неверный код подтверждения. Должно быть 7 символов. Пример: xxx-xxx");
        }
         VerificationCode currentVerificationCode = verificationCodeRepository
                 .getByApplicantApplicationId(verificationCode.getApplicantApplicationId())
                 .orElseThrow(() -> new BaseBusinessLogicException("Неверный код подтверждения. Повторите еще раз!"));

         if (!currentVerificationCode.getCode().equals(verificationCode.getCode())) {
             throw new BaseBusinessLogicException("Неверный код подтверждения. Повторите еще раз!");
         }

         if (LocalDateTime.now().isAfter(currentVerificationCode.getExpireDate())){
             throw new BaseBusinessLogicException("Код подтверждения истек. Запросите новый");
         }

        applicationRepository
                .findById(verificationCode.getApplicantApplicationId())
                .orElseThrow(() -> new BaseBusinessLogicException("Ошибка подтверждения почты. Попробуйте еще раз."))
                .setIsEmailActivated(true);

         currentVerificationCode.setIsApplied(true);

         return true;
    }
    @Override
    public VerificationCode generateCode(Long applicantApplicationId){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<6;i++){
            builder.append(chars.charAt(new Random().nextInt(chars.length())));
            if(i == 2){
                builder.append("-");
            }
        }
        VerificationCode verCode = new VerificationCode();
        verCode.setCode(builder.toString());
        verCode.setExpireDate(LocalDateTime.now().plusDays(1));
        verCode.setIsApplied(false);
        verCode.setApplicantApplicationId(applicantApplicationId);
        verificationCodeRepository.save(verCode);
        return verCode;
    }
}
