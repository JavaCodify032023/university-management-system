package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.PersonData;

public interface PersonService {
    PersonData findByPN(Long pn);
    Long addNewPerson(PersonData personData);
}
