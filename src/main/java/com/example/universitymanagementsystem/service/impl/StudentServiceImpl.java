package com.example.universitymanagementsystem.service.impl;

import com.example.universitymanagementsystem.entity.Student;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.repository.StudentRepository;
import com.example.universitymanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Long save(Student student) {
        try{
            return studentRepository.save(student).getId();
        }catch (Exception e){
            throw new BaseBusinessLogicException("Не удалось сохранить студента");
        }
    }
}
