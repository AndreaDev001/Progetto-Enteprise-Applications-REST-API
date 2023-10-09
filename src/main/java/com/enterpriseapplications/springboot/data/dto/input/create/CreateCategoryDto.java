package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryDto
{
    @NotNull
    @NotBlank
    private String primary;

    @NotNull
    @NotBlank
    private String secondary;

    @NotNull
    @NotBlank
    private String tertiary;
}
