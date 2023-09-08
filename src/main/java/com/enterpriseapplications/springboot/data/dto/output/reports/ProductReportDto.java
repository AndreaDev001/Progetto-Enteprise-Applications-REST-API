package com.enterpriseapplications.springboot.data.dto.output.reports;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReportDto extends ReportDto {
    private String details;
    private String name;
    private String description;
}
