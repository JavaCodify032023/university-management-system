package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.SpecialtyAdmissionRepository;
import com.example.universitymanagementsystem.service.SpecialtyAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialtyAdmissionServiceImpl implements SpecialtyAdmissionService {

    private final SpecialtyAdmissionRepository specialtyAdmissionRepository;

    @Override
    public List<SpecialtyAdmission> getActiveAdmissions(){
        List<SpecialtyAdmission> allActive = specialtyAdmissionRepository.getAllActive();
        if(allActive.isEmpty()){
            throw new BaseBusinessLogicException("Наборы не объявлены");
        }else {
            return allActive;
        }
    }

    @Override
    public List<SpecialtyAdmission> getActiveAdmissions(Long facultyId) {
        List<SpecialtyAdmission> allActiveBySpecId = specialtyAdmissionRepository.getAllActive(facultyId);
        if(allActiveBySpecId.isEmpty()){
            throw new BaseBusinessLogicException("Набор не объявлены");
        }
        return allActiveBySpecId;
    }

    @Override
    public List<SpecialtyAdmission> createNewAdmission(SpecialtyAdmission specialtyAdmission){
        List<SpecialtyAdmission> specActive = specialtyAdmissionRepository.getActiveBySpecialtyId(
                specialtyAdmission.getSpecialty().getId());
        if(!specActive.isEmpty()) {
            throw new BaseBusinessLogicException("Уже существует действующий набор на эту специальнсоть");
        }else {
            specActive.add(specialtyAdmission);

            List<SpecialtyAdmission> result = specActive.stream()
                    .filter(x -> x.getGroupCapacity() >= 3)
                    .filter(x -> x.getGroupAmount() >= 1)
                    .filter(x -> x.getStartDate().isBefore(LocalDateTime.now()))
                    .toList();
            if (result.isEmpty()) {
                throw new BaseBusinessLogicException("Некорректный ввод данные");
            }else
                try {
                specialtyAdmissionRepository.save(specialtyAdmission);
            }catch (Exception e){
                throw new BaseBusinessLogicException("Неудалось создать набор");
            }
        }
        return specActive;
    }
}
