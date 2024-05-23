package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.SpecialtyAdmissionRepository;
import com.example.universitymanagementsystem.service.SpecialtyAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                specialtyAdmission.getSpecialty().getId()).orElseThrow(()-> new BaseBusinessLogicException("Некорректный ввод данных"));
        if(specActive.isEmpty()){
            try {
                specActive.add(specialtyAdmissionRepository.save(specialtyAdmission));
            }catch (Exception e){
                throw new BaseBusinessLogicException(e.getMessage());
            }
        }
        else throw new BaseBusinessLogicException("Уже есть действующий набор на данную специальность");
        return specActive;
    }
}
