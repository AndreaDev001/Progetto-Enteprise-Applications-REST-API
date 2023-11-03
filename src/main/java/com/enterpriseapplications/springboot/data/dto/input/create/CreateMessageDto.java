package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMessageDto
{
    @NotNull
    private UUID receiverID;

    @NotNull
    private UUID conversationID;

    @NotNull
    @NotBlank
    private String text;
}
