package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.ApplicantApplicationRepository;
import com.example.universitymanagementsystem.entity.applyment.Candidate;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.entity.applyment.VerificationCode;
import com.example.universitymanagementsystem.entity.uni_struct.Specialty;
import com.example.universitymanagementsystem.repository.CandidateRepository;
import com.example.universitymanagementsystem.repository.SpecialtyAdmissionRepository;
import com.example.universitymanagementsystem.service.EmailService;
import com.example.universitymanagementsystem.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ApplicantApplicationServiceImplTest {
    @InjectMocks
    private ApplicantApplicationServiceImpl applicantApplicationService;
    @Mock
    private VerificationCodeService verificationCodeService;

    @Spy
    private ApplicantApplicationRepository applicantApplicationRepository;
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private SpecialtyAdmissionRepository specialtyAdmissionRepository;


    @Mock
    private EmailService emailService;

    @Test
    void getEmailVerifiedApplicants_OK() {
        ApplicantApplication applicantApplication = new ApplicantApplication();
        applicantApplication.setEmail("test@gmail.com");
        when (applicantApplicationRepository.getAllNonCheckedActivated()).thenReturn(List.of(applicantApplication));
        List<ApplicantApplication> emailVerifiedApplicants = applicantApplicationService.getEmailVerifiedApplicants();
        Assertions.assertEquals(emailVerifiedApplicants.size(),1);
        Assertions.assertEquals(emailVerifiedApplicants.get(0),applicantApplication);
    }

    @Test
    void getEmailVerifiedApplicants_nothing() {
        when(applicantApplicationRepository.getAllNonCheckedActivated()).thenReturn(List.of());
        BaseBusinessLogicException exception = assertThrows(BaseBusinessLogicException.class, () -> applicantApplicationService.getEmailVerifiedApplicants());
        Assertions.assertEquals(exception.getMessage(), "Нету заявок абитуриентов на проверку");
    }


    @Test()
    void registerApplicantApplication__waiting_for_ver(){
        ApplicantApplication application = new ApplicantApplication();
        application.setPersonalNumber(1L);
        when(applicantApplicationRepository.findByPnWhichIsNonChecked(anyLong())).thenReturn(Optional.of(new ApplicantApplication()));

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                applicantApplicationService.registerApplicantApplication(application));
        assertEquals(baseBusinessLogicException.getMessage(),"Ваше заявление ожидает проверки");
    }
    @Test()
    void registerApplicantApplication__already_candidate(){
        ApplicantApplication application = new ApplicantApplication();
        application.setPersonalNumber(1L);

        when(applicantApplicationRepository.findByPnWhichIsNonChecked(anyLong())).thenReturn(Optional.empty());
        Specialty specialty = new Specialty();
        specialty.setName("Программная инженерия");
        application.setSpecialty(specialty);
        Candidate candidate = new Candidate();
        candidate.setApplicantApplication(application);
        when(candidateRepository.findActiveByPn(anyLong())).thenReturn(Optional.of(candidate));

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                applicantApplicationService.registerApplicantApplication(application));
        assertEquals(baseBusinessLogicException.getMessage(),"Вы уже числитесь кандидатом по направлению \"Программная инженерия\"");
    }

    @Test()
    void registerApplicantApplication__admission_not_active(){
        ApplicantApplication application = new ApplicantApplication();
        application.setPersonalNumber(1L);
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        application.setSpecialty(specialty);
        when(applicantApplicationRepository.findByPnWhichIsNonChecked(anyLong())).thenReturn(Optional.empty());
        when(candidateRepository.findActiveByPn(anyLong())).thenReturn(Optional.empty());
        when(specialtyAdmissionRepository.getActiveBySpecId(anyLong())).thenReturn(Optional.empty());

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () ->
                applicantApplicationService.registerApplicantApplication(application));
        assertEquals(baseBusinessLogicException.getMessage(),"Набор по выбранному направлению не активен");
    }

    @Test
    void registerApplicantApplication_success() {
        ApplicantApplication application = new ApplicantApplication();
        application.setFirstName("Саринжан");
        application.setPersonalNumber(2312L);
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Программная инженерия");
        application.setSpecialty(specialty);
        application.setEmail("sarinzhankazbekov@gmail.com");

        when(applicantApplicationRepository.findByPnWhichIsNonChecked(anyLong())).thenReturn(Optional.empty());
        when(candidateRepository.findActiveByPn(anyLong())).thenReturn(Optional.empty());
        when(specialtyAdmissionRepository.getActiveBySpecId(anyLong())).thenReturn(Optional.of(new SpecialtyAdmission()));

        application.setId(1L);
        when(applicantApplicationRepository.save(any(ApplicantApplication.class))).thenReturn(application);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode("122-421");
        when(verificationCodeService.generateCode(anyLong())).thenReturn(verificationCode);

        Long l = applicantApplicationService.registerApplicantApplication(application);
        Assertions.assertEquals(1,l);

        verify(emailService,times(1)).sendMessage( anyString(),anyString(),anyString());
    }

    @Test
    void generateText_success(){
        ApplicantApplication application = new ApplicantApplication();
        application.setFirstName("Саринжан");
        Specialty specialty = new Specialty();
        specialty.setName("Программная инженерия");
        application.setSpecialty(specialty);
        String code = "3fd-gsd";
        String result = applicantApplicationService.generateText(code, application);
        String expected = " \n" +
                 "Саринжан, здравствуйте! \n" +
                " \n" +
                "Благодарим вас за регистрацию на набор по направлению Программная инженерия в Кыргызско-Российско Славянском Университете. Для активации вашей кандидатуры и начала процесса отбора, пожалуйста, используйте следующий код активации: \n" +
                " \n" +
                "Код активации: 3fd-gsd\n" +
                " \n" +
                "После подтверждения кода, ваша заявка будет отправлена на проверку. Мы внимательно изучим предоставленные данные и свяжемся с вами с результатами проверки.\n" +
                " \n" +
                "Если вы не регистрировались в данной программе и получили это письмо по ошибке, пожалуйста, проигнорируйте его. \n" +
                " \n" +
                "С уважением, \n" +
                "Приемная комиссия КРСУ";
        assertEquals(expected,result);
    }
    @Test
    void generateText_no_code(){
        ApplicantApplication application = new ApplicantApplication();
        application.setFirstName("Саринжан");
        Specialty specialty = new Specialty();
        specialty.setName("Программная инженерия");
        application.setSpecialty(specialty);

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () -> applicantApplicationService.generateText("", application));
        assertEquals(baseBusinessLogicException.getMessage(),"Невозможно отправить код подтверждения");
    }
    @Test
    void generateText_empty_firstName(){
        ApplicantApplication application = new ApplicantApplication();
        application.setFirstName("");
        Specialty specialty = new Specialty();
        specialty.setName("Программная инженерия");
        application.setSpecialty(specialty);

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () -> applicantApplicationService.generateText("ds", application));
        assertEquals(baseBusinessLogicException.getMessage(),"Невозможно отправить код подтверждения");
    }
    @Test
    void generateText_empty_specialty_name(){
        ApplicantApplication application = new ApplicantApplication();
        application.setFirstName("Саринжан");
        Specialty specialty = new Specialty();
        specialty.setName("");
        application.setSpecialty(specialty);

        BaseBusinessLogicException baseBusinessLogicException = assertThrows(BaseBusinessLogicException.class, () -> applicantApplicationService.generateText("ds", application));
        assertEquals(baseBusinessLogicException.getMessage(),"Невозможно отправить код подтверждения");
    }
}