package com.example.universitymanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Admission candidates information. By this information candidates could find out information about another candidates")
public class AdmissionCandidatesResponseDto {
    private Integer testScore;
    private String firstName;
    private String middleName;
    private String lastName;
    @Schema(description = "True means that candidate recommended for admission")
    private Boolean isRecommended;
}
