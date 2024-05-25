package com.example.universitymanagementsystem.controller;

import com.example.universitymanagementsystem.dto.response.CommonResponseDto;
import com.example.universitymanagementsystem.dto.response.PersonDataResponseDto;
import com.example.universitymanagementsystem.exception.BaseBusinessLogicException;
import com.example.universitymanagementsystem.mapper.PersonDataMapper;
import com.example.universitymanagementsystem.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Person", description = "APIs for person")
@RequestMapping("/person")
@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonDataMapper personDataMapper;
    private final PersonService personService;

    @GetMapping("/find-by-pn")
    public CommonResponseDto<PersonDataResponseDto> findByPN(@RequestParam Long pn){
        PersonDataResponseDto personDataResponseDto = personDataMapper
                .entityToDto(personService.findByPN(pn));
        return new CommonResponseDto<PersonDataResponseDto>().setOk().setData(personDataResponseDto);
    }
}
