package com.example.universitymanagementsystem;

import com.example.universitymanagementsystem.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

// для интеграционного теста поднимаем весь контекст
// и поднимаем на рандомном свободном порте
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(value = AppConfiguration.class)
@AutoConfigureMockMvc
public abstract class BaseIT {
    @LocalServerPort
    public int port;

    @Autowired
    public MockMvc mockMvc;
    public String BASE_URL;
}
