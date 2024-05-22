package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.entity.applyment.VerificationCode;
import com.example.universitymanagementsystem.exception.*;
import com.example.universitymanagementsystem.repository.ApplicantApplicationRepository;
import com.example.universitymanagementsystem.repository.CandidateRepository;
import com.example.universitymanagementsystem.repository.SpecialtyAdmissionRepository;
import com.example.universitymanagementsystem.service.ApplicantApplicationService;
import com.example.universitymanagementsystem.service.EmailService;
import com.example.universitymanagementsystem.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplicantApplicationServiceImpl implements ApplicantApplicationService {
    private final CandidateRepository candidateRepository;
    private final ApplicantApplicationRepository applicantApplicationRepository;
    private final SpecialtyAdmissionRepository specialtyAdmissionRepository;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;

    /**
     * If applicant by specified PN already waiting for verify it will throw exception.
     * If applicant is candidate already it will throw exception
     * If chosen specialty admission is not available it will throw exception
     * Sent code to specified email to verify email.
     * @param app
     * @return Long - created applicant application id
     */
    @Override
    public Long registerApplicantApplication(ApplicantApplication app){
        if(applicantApplicationRepository.findByPnWhichIsNonChecked(app.getPersonalNumber())
                .isPresent()){
            throw new BaseBusinessLogicException("Ваше заявление ожидает проверки");
        }
        candidateRepository.findActiveByPn(app.getPersonalNumber())
                .ifPresent(x -> {
                    throw new BaseBusinessLogicException("Вы уже числитесь кандидатом по направлению \"%s\"".formatted(x.getApplicantApplication().getSpecialty().getName()));});

        Integer requiredScore = specialtyAdmissionRepository
                .getActiveBySpecId(app.getSpecialty().getId())
                .orElseThrow(() -> new BaseBusinessLogicException("Набор по выбранному направлению не активен"))
                .getMinScore();

        if(app.getTestScore() < requiredScore){
            throw new BaseBusinessLogicException("Для поступления требуется %d и более баллов".formatted(requiredScore));
        }

        ApplicantApplication applicantApplication = applicantApplicationRepository.save(app);

        VerificationCode verificationCode = verificationCodeService.generateCode(applicantApplication.getId());
        Long id = applicantApplicationRepository.save(app).getId();

        String subject = "Подтверждение адреса электронной почты и активация кандидатуры";
        String message = generateText(verificationCode.getCode(),applicantApplication);
        emailService.sendMessage(app.getEmail(), subject, message);
        return id;
    }
    @Override
    public Long saveApp(ApplicantApplication applicantApplication) {
        try {
            return applicantApplicationRepository.save(applicantApplication).getId();
        } catch (Exception ex) {
            throw new BaseBusinessLogicException("Не удалось сохранить заявку абитуриента");
        }
    }
    @Override
    public List<ApplicantApplication> getEmailVerifiedApplicants(){
        List<ApplicantApplication> applicantApplicationsList = applicantApplicationRepository.getAllNonCheckedActivated();
        if(applicantApplicationsList.isEmpty()){
            throw new BaseBusinessLogicException("Нету заявок абитуриентов на проверку");
        }
        return applicantApplicationsList;
    }

    @Override
    public List<ApplicantApplication> getByAdmissionId(Long admissionId) {
        List<ApplicantApplication> allByAdmissionId = applicantApplicationRepository.getAllByAdmissionId(admissionId);
        if(allByAdmissionId.isEmpty()){
            throw new BaseBusinessLogicException("Заявления абитуриентов не найдено");
        }
        return allByAdmissionId;
    }

    public String generateText(String code,ApplicantApplication applicantApplication){
        if(code.isEmpty() ||
                applicantApplication.getFirstName().isEmpty() ||
                applicantApplication.getSpecialty().getName().isEmpty()
        ){
            throw new BaseBusinessLogicException("Невозможно отправить код подтверждения");
        }
        return " \n" +
                applicantApplication.getFirstName() +", здравствуйте! \n" +
                " \n" +
                "Благодарим вас за регистрацию на набор по направлению "+ applicantApplication.getSpecialty().getName() + " в Кыргызско-Российско Славянском Университете. Для активации вашей кандидатуры и начала процесса отбора, пожалуйста, используйте следующий код активации: \n" +
                " \n" +
                "Код активации: "+ code + "\n" +
                " \n" +
                "После подтверждения кода, ваша заявка будет отправлена на проверку. Мы внимательно изучим предоставленные данные и свяжемся с вами с результатами проверки.\n" +
                " \n" +
                "Если вы не регистрировались в данной программе и получили это письмо по ошибке, пожалуйста, проигнорируйте его. \n" +
                " \n" +
                "С уважением, \n" +
                "Приемная комиссия КРСУ";
    }
}
