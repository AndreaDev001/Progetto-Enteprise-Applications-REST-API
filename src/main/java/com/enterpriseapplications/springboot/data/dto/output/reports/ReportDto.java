package com.enterpriseapplications.springboot.data.dto.output.reports;


import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto extends GenericOutput<ReportDto>
{
    protected UserRef reporter;
    protected UserRef reported;
    protected ReportReason reason;
    protected ReportType type;
    protected LocalDate createdDate;
}
