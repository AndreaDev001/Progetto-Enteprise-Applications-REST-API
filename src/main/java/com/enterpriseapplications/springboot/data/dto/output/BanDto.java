package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanDto
{
    private UserRef banner;
    private UserRef banned;
    private ReportReason reason;
    private boolean expired;
    private LocalDate createdDate;
    private LocalDate expirationDate;
}
