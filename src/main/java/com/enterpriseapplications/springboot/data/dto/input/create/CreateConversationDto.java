package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateConversationDto
{
    @NotNull
    private UUID second;
    @NotNull
    private UUID productID;
}
