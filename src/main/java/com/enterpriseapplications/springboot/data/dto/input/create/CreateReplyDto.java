package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReplyDto
{

    @NotNull
    @PositiveOrZero
    private Long reviewID;

    @NotNull
    @NotBlank
    private String text;
}
