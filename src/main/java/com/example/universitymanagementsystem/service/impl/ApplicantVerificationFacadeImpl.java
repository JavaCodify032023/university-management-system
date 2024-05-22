package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.PersonData;
import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.entity.applyment.Candidate;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.mapper.EntityToEntityMapper;
import com.example.universitymanagementsystem.repository.ApplicantApplicationRepository;
import com.example.universitymanagementsystem.service.ApplicantApplicationService;
import com.example.universitymanagementsystem.service.ApplicantVerificationFacade;
import com.example.universitymanagementsystem.service.EmailService;
import com.example.universitymanagementsystem.service.PersonDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApplicantVerificationFacadeImpl implements ApplicantVerificationFacade {
    private final CandidateServiceImpl candidateService;
    private final ApplicantApplicationService applicantApplicationService;
    private final EmailService emailService;
    private final PersonDataService personDataService;

    private final ApplicantApplicationRepository applicantApplicationRepository;

    private final EntityToEntityMapper mapper;

    @Override
    @Transactional
    public Boolean verifyApplicantApplication(Long applicantApplicationId, String reason, Boolean decision) {
        ApplicantApplication applicantApplication =  applicantApplicationRepository.findById(applicantApplicationId)
                .orElseThrow(() -> new BaseBusinessLogicException("Не найдена заявка абитуриента"));

        applicantApplication.setIsChecked(true);
        applicantApplication.setIsDeclined(!decision);
        applicantApplication.setIsAccepted(decision);
        applicantApplicationService.saveApp(applicantApplication);

        Candidate candidate = Candidate
                .builder()
                .applicantApplication(applicantApplication)
                .department(applicantApplication.getDepartment())
                .testScore(applicantApplication.getTestScore())
                .specialtyAdmission(applicantApplication.getSpecialtyAdmission())
                .build();
        candidateService.addCandidate(candidate);

        PersonData personData = mapper.applicantApplicationToPersonData(applicantApplication);
        Long personDataId = personDataService.save(personData);

        String message = checkingMessage(reason,decision,applicantApplication.getFirstName());
        emailService.sendMessage(applicantApplication.getEmail(),"Заявка на поступление",message);

        return true;
    }

    private String checkingMessage(String message, Boolean verify, String nameOfApplicant) {
        if (verify) {
            return nameOfApplicant + ", здравствуйте! \n" +
                    " \n" +
                    "Поздравляем!" + "\n" +
                    " \n" +
                    "Вы были рекомендованы к зачислению!";
        } else if(Objects.nonNull(message)) {
            return nameOfApplicant + ", здравствуйте! \n" +
                    " \n" +
                    "Извините, вы не были рекомендованы к зачислению!\n" +
                    "По причинине: " + message;
        }else {
            return nameOfApplicant + ", здравствуйте! \n" +
                    " \n" +
                    "Извините, вы не были рекомендованы к зачислению!";
        }
    }
}

