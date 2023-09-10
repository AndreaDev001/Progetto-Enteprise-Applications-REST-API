package com.enterpriseapplications.springboot.data.dto.output.reports;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto
{
    protected UserRef reporter;
    protected UserRef reported;
    protected ReportReason reason;
    protected ReportType type;
    protected LocalDate createdDate;
}
