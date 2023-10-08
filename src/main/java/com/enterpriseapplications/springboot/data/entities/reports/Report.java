package com.enterpriseapplications.springboot.data.entities.reports;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.OwnableEntity;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "REPORTS",uniqueConstraints = {@UniqueConstraint(columnNames = {"REPORTED","REPORTER"})})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "DESCRIPTION",unique = false)
    @Convert(converter = TrimConverter.class)
    protected String description;

    @Column(name = "REASON",unique = false)
    @Enumerated(EnumType.STRING)
    protected ReportReason reason;

    @Column(name = "TYPE",unique = false)
    @Enumerated(EnumType.STRING)
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

    @Override
    public UUID getOwnerID() {
        return this.getReporter().getId();
    }
}
