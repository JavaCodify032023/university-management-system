package com.example.universitymanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Schema(description = "Запрос на создание кового набора")
@NotBlank(message = "Поля не могут быть пустыми")
public class CreateAdmissionRequestDto {
    @Schema(description = "ID факультета", example = "1")
    private Long facultyId;

    @Schema(description = "ID департамента", example = "2")
    private Long departmentId;

    @Schema(description = "ID специальности", example = "3")
    private Long specialtyId;

    @Schema(description = "Количество групп на набор по специальности", example = "1")
    @Size(min = 1, message = "Минимальное количество групп должно быть не меньше 1")
    private Integer groupAmount;

    @Schema(description = "Количество студентов в каждой группе", example = "1")
    @Size(min = 3, message = "Минимальное количество студентов должно быть не меньше 3")
    private Integer groupCapacity;

    @Schema(description = "Минимальный проходной балл для этого набора", example = "1")
    private Integer minScore;

    @Schema(description = "Дата старта наора", example = "2025-07-07T09:11:21")
    @Size(message = "Дата старта не может быть раньше текущей даты")
    private LocalDateTime startDate;

    @Schema(description = "Дата окончания набора", example = "2025-07-07T09:11:21")
    @Size(message = "Дата окончания не может быть раньше даты начала")
    private LocalDateTime endDate;

    public CreateAdmissionRequestDto() {
    }

}
