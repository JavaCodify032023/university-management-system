package com.example.universitymanagementsystem.controller;

import com.example.universitymanagementsystem.BaseIT;
import com.example.universitymanagementsystem.entity.applyment.ApplicantApplication;
import com.example.universitymanagementsystem.service.impl.ApplicantApplicationServiceImpl;
import lombok.With;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ApplicantControllerTest extends BaseIT {
    @MockBean
    private ApplicantApplicationServiceImpl applicantApplicationService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getToVerify_OK() throws Exception {
        ApplicantApplication applicantApplication = new ApplicantApplication();
        applicantApplication.setEmail("test@gmail.com");
        when(applicantApplicationService.getEmailVerifiedApplicants()).thenReturn(List.of(applicantApplication));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"applicant/get-all-to-verify")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Hello Sarinzhan"));
    }



    @BeforeEach
    void setup(){
        this.BASE_URL = "http://localhost:%d/api/university-management-system".formatted(this.port);
    }

}