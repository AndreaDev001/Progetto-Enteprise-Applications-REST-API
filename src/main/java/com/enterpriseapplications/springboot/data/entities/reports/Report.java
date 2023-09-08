package com.enterpriseapplications.springboot.data.entities.reports;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Table(name = "REPORTS",uniqueConstraints = {@UniqueConstraint(columnNames = {"REPORTER","REPORTED"})})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "DESCRIPTION",unique = false)
    @Convert(converter = TrimConverter.class)
    protected String description;

    @Column(name = "REASON",unique = false)
    protected ReportReason reason;

    @Column(name = "TYPE",unique = false)
    protected ReportType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER")
    protected User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTED")
    protected User reported;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    protected LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    protected LocalDate lastModifiedDate;
}
