package com.enterpriseapplications.springboot.data.dto.input.update;

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
public class UpdateAddressDto
{
    @NotNull
    private UUID addressID;
    private String ownerName;
}
