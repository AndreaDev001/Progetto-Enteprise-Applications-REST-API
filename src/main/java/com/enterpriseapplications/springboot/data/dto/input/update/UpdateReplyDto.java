package com.enterpriseapplications.springboot.data.dto.input.update;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReplyDto
{
    @NotNull
    @PositiveOrZero
    private Long replyID;

    @NotNull
    @NotBlank
    private String text;
}