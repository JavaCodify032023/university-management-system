package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.applyment.Candidate;
import com.example.universitymanagementsystem.entity.applyment.SpecialtyAdmission;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {
    @InjectMocks
    private CandidateServiceImpl candidateService;
    @Mock
    private CandidateRepository candidateRepository;
    @Test
    void getActive_success() {
        List<Candidate> candidateList = new ArrayList<>();

        SpecialtyAdmission specialtyAdmission = new SpecialtyAdmission();
        specialtyAdmission.setGroupAmount(2);
        specialtyAdmission.setGroupCapacity(1);

        Candidate candidate1 = new Candidate();
        candidate1.setTestScore(95);
        candidate1.setSpecialtyAdmission(specialtyAdmission);
        Candidate candidate2 = new Candidate();
        candidate2.setTestScore(130);
        candidate2.setSpecialtyAdmission(specialtyAdmission);
        Candidate candidate3 = new Candidate();
        candidate3.setTestScore(120);
        candidate2.setSpecialtyAdmission(specialtyAdmission);

        candidateList.add(candidate1);
        candidateList.add(candidate2);
        candidateList.add(candidate3);

        when(candidateRepository.findAllByAdmissionId(anyLong())).thenReturn(candidateList);
        List<Candidate> result = candidateService.getAllActiveByAdmissionId(1L);
        assertEquals(3,result.size());
        assertEquals(true,result.get(0).getIsRecommended());
        assertEquals(true,result.get(1).getIsRecommended());
        assertEquals(false,result.get(2).getIsRecommended());

        assertEquals(130,result.get(0).getTestScore());
        assertEquals(95,result.get(2).getTestScore());
    }

    @Test
    void getActive__no_candidates(){
        when(candidateRepository.findAllByAdmissionId(anyLong())).thenReturn(List.of());
        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> candidateService.getAllActiveByAdmissionId(1L));
        assertEquals("Кандидаты отсутствуют",exception.getMessage());
    }

    @Test
    void addCandidate_success() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);
        Long res = candidateService.addCandidate(candidate);
        assertEquals(1L,res);
    }

    @Test
    void addCandidate_failed(){
        when(candidateRepository.save(any(Candidate.class))).thenThrow(new RuntimeException("Не удалось добавить кандидата"));
        BaseBusinessLogicException exception =
                assertThrows(BaseBusinessLogicException.class, () -> candidateService.addCandidate(new Candidate()));
        assertEquals("Не удалось добавить кандидата",exception.getMessage());
    }
}