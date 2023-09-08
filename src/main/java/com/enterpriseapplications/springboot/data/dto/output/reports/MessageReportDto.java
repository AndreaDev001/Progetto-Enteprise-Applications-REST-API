package com.enterpriseapplications.springboot.data.dto.output.reports;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReportDto extends ReportDto
{
    private Long messageID;
    private String messageText;
}
