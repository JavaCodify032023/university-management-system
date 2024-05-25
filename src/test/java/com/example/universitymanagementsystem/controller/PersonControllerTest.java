package com.example.universitymanagementsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port; //так как у нас приложения пооднимается на рандомном свободном порте, нам надо узнать где он

    private String BASE_URL;

    @BeforeEach
    void setup(){
        this.BASE_URL = "http://localhost:%d/".formatted(this.port);
    }

    @WithMockUser(roles = "admin")
    @Test
    void testApiPersonTest_OK() throws Exception {
        //если pathVariable то указываем их в строке
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"person/hello").param("name","Sarinzhan")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Hello Sarinzhan"));
    }
}
