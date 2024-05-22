package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.Student;
import com.example.universitymanagementsystem.entity.User;
import com.example.universitymanagementsystem.entity.applyment.Candidate;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.entity.uni_struct.Group;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.mapper.EntityToEntityMapper;
import com.example.universitymanagementsystem.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CandidateGroupDistributionFacadeImpl implements CandidateGroupDistributionService{
    private final CandidateService candidateService;
    private final ApplicantApplicationService applicantApplicationService;
    private final SpecialtyAdmissionService specialtyAdmissionService;
    private final PersonDataService personDataService;
    private final GroupService groupService;
    private final StudentService studentService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final EntityToEntityMapper mapper;
    private final Map<Long,String> jobUuidMap = new HashMap<>();
    private final TaskSchedulingService taskSchedulingService;

    public void scheduleDistributing(SpecialtyAdmission specialtyAdmission){
        String jobUuid = taskSchedulingService.scheduleTaskWithFixedDelay(
                distributeCandidates(specialtyAdmission.getId()),
                specialtyAdmission.getEndDate(),
                Duration.ofHours(1)
        );
        jobUuidMap.put(specialtyAdmission.getId(),jobUuid);
    }
    @Transactional 
    public Runnable distributeCandidates(Long admissionId){
        return  () -> {
            SpecialtyAdmission admission = specialtyAdmissionService.getById(admissionId);
            //check if applicant application is empty
            applicantApplicationService.getByAdmissionId(admissionId)
                    .stream()
                    .filter(x -> x.getIsChecked().equals(false))
                    .findFirst()
                    .ifPresent(x -> {throw new BaseBusinessLogicException("Заявки абитуриентов ожидают проверки");});

            List<Candidate> candidateList = candidateService.getAllByAdmissionId(admissionId)
                    .stream()
                    .filter(x -> admission.getMinScore() <= x.getTestScore())
                    .sorted(Comparator.comparing(Candidate::getTestScore).reversed())
                    .limit((long) admission.getGroupAmount() * admission.getGroupCapacity())
                    .toList();

            divide(candidateList, admission.getGroupAmount())
                    .stream()
                    .map(x -> initStudentAndGroup(x,admission))
                    .peek(x -> x.stream().peek(this::initUser));

            taskSchedulingService.removeTask(jobUuidMap.get(admissionId))

        };
    }

    private List<List<Candidate>> divide(List<Candidate> candidateList,Integer groupAmount){
        List<List<Candidate>> resultList = new ArrayList<>();
        for(int i=0;i<groupAmount;i++){
            List<Candidate> temp = new ArrayList<>();
            for(int j=i;j<candidateList.size();j+=groupAmount){
                temp.add( candidateList.get(i));
            }
            resultList.add(temp);
        }
        return resultList;
    }
    private List<Student> initStudentAndGroup(List<Candidate> candidateList, SpecialtyAdmission admission){
        Group group = new Group();
        group.setName(admission.getSpecialty().getName());
        group.setSpecialty(admission.getSpecialty());
        Long groupId = groupService.save(group);
        group.setId(groupId);
        return candidateList
                 .stream()
                 .map(x ->
                        Student.builder()
                                .application(x.getApplicantApplication())
                                .group(group)
                                .personData(personDataService.findByPn(x.getApplicantApplication().getPersonalNumber()))
                                .build())
                 .peek(studentService::save)
                 .toList();
    }

    private void initUser(Student student){
        User user = new User();
        user.setUserRole(userRoleService.findByName("STUDENT"));
        user.setPersonData(student.getPersonData());
        user.setIsActive(true);
        user.setLogin(student.getPersonData().getFirstName());
        String password = Integer.toString(new Random().nextInt(100000, 999999));
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userService.save(user);
        emailService.sendMessage(student.getApplication().getEmail(), "Университет", generateMessage(student,password));
    }

    private String generateMessage(Student student,String password){
        return  "Мы рады сообщить Вам, что Вы были успешно зачислены!\n" +
                "Ваш данные для входа в ИАИС\n" +
                "Login: %s\nPassword:%s".formatted(student.getPersonData().getFirstName(),password);
    }
}
