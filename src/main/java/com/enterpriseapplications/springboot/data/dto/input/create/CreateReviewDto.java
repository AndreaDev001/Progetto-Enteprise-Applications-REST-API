package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReviewDto {
    
    @NotNull
    private UUID reviewedID;

    @NotNull
    @NotBlank
    private String text;

    @NotNull
    @Positive
    @Max(10)
    private Integer rating;
}
