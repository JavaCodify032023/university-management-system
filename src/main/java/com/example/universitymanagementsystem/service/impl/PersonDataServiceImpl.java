package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.PersonData;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.PersonRepository;
import com.example.universitymanagementsystem.service.PersonDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDataServiceImpl implements PersonDataService {
    private final PersonRepository personRepository;
    @Override
    public Long save(PersonData personData) {
        try{
            personRepository.save(personData);
        }catch (Exception ex){
            throw new BaseBusinessLogicException("Не удалось сохранить данные");
        }
    }

    @Override
    public PersonData findByPn(Long personalNumber) {
        return personRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() ->new BaseBusinessLogicException("Не удалось найти"));
    }
}
