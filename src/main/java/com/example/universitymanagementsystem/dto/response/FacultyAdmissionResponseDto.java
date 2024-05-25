package com.example.universitymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Faculty info response dto, for applicants")
public class FacultyAdmissionResponseDto {
    private Long facultyId;
    private String facultyName;
}
