package com.enterpriseapplications.springboot.data.dto.input;


import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportDto
{
    @NotNull
    @PositiveOrZero
    protected Long reportedID;

    @NotNull
    @NotBlank
    protected ReportReason reason;

    @NotNull
    @NotBlank
    protected ReportType type;
}
