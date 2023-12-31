package com.enterpriseapplications.springboot.data.dto.input.create;


import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBanDto {

    @NotNull
    private UUID bannedID;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private ReportReason reason;

    @NotNull
    @Future
    private LocalDate expirationDate;
}
