package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.Candidate;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.CandidateRepository;
import com.example.universitymanagementsystem.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

    @Override
    public List<Candidate> getAllActiveByAdmissionId(Long admissionId) {
        List<Candidate> candidates = candidateRepository.findAllActiveByAdmissionId(admissionId)
                .stream()
                .sorted(Comparator.comparing(Candidate::getTestScore).reversed())
                .peek(x -> x.setIsRecommended(false))
                .toList();
        if(candidates.isEmpty()){
            throw new BaseBusinessLogicException("Кандидаты отсутствуют");
        }
        SpecialtyAdmission specialtyAdmission = candidates.get(0).getSpecialtyAdmission();
        int totalCapacity = specialtyAdmission.getGroupCapacity() * specialtyAdmission.getGroupAmount();
        for(int i=0;i<totalCapacity && i < candidates.size();i++){
            candidates.get(i).setIsRecommended(true);
        }
        return candidates;
    }

    @Override
    public Long addCandidate(Candidate candidate) {
        try {
            return candidateRepository.save(candidate).getId();
        }catch (Exception ex){
            throw new BaseBusinessLogicException("Не удалось добавить кандидата");
        }
    }

    @Override
    public List<Candidate> getAllByAdmissionId(Long admissionId) {
        List<Candidate> allByAdmissionId = candidateRepository.findAllByAdmissionId(admissionId);
        if(allByAdmissionId.isEmpty()){
            throw new BaseBusinessLogicException("Не удалось найти кандидатов по набору");
        }
        return allByAdmissionId;
    }
}

