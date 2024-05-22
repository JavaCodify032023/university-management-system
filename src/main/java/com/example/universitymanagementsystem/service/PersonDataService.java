package com.example.universitymanagementsystem.service;

import com.example.universitymanagementsystem.entity.PersonData;

public interface PersonDataService {
    Long save(PersonData personData);
    PersonData findByPn(Long personalNumber);
}
