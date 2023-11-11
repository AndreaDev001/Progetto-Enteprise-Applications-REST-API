package com.enterpriseapplications.springboot.data.entities.reports;


import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Table(name = "MESSAGE_REPORTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReport extends Report {

    public MessageReport(Report report) {
        this.setId(report.getId());
        this.setDescription(report.getDescription());
        this.setReason(report.getReason());
        this.setType(ReportType.MESSAGE);
        this.setReporter(report.getReporter());
        this.setReported(report.getReported());
        this.setCreatedDate(report.getCreatedDate());
        this.setLastModifiedDate(report.getLastModifiedDate());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID",nullable = false)
    private Message message;
}
