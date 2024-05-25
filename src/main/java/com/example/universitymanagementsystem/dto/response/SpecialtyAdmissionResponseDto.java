package com.example.universitymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Specialty admission info")
public class SpecialtyAdmissionResponseDto {

    @Schema(description = "Admission id")
    private Long admissionId;

    @Schema(description = "Specialty id")
    private Long specialtyId;

    @Schema(description = "Department id")
    private Long departmentId;

    @Schema(description = "Specialty name")
    private String specialtyName;

    @Schema(description = "Total number of seat")
    private Integer groupCapacity;//number of seat
}
