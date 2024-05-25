package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.PersonData;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.PersonRepository;
import com.example.universitymanagementsystem.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public PersonData findByPN(Long pn) {
        return personRepository
                .findByPersonalNumber(pn)
                .orElseThrow(() -> new BaseBusinessLogicException("Person by " + pn + " PN not found"));

    }

    @Override
    public Long addNewPerson(PersonData personData){
        personRepository
                .findByPersonalNumber(personData.getPersonalNumber())
                .orElseThrow(() -> new BaseBusinessLogicException("Personal number %d already exist".formatted(personData.getPersonalNumber())));
        return personRepository.save(personData).getId();
    }
}
