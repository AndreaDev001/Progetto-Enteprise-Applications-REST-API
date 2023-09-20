package com.enterpriseapplications.springboot.data.dto.input.update;


import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReportDto
{
    @NotNull
    @PositiveOrZero
    private Long reportID;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private ReportReason reason;
}
