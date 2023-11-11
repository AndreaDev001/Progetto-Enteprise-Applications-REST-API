package com.enterpriseapplications.springboot.data.entities.reports;


import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Table(name = "PRODUCT_REPORTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReport extends Report
{

    public ProductReport(Report report) {
        this.setId(report.getId());
        this.setDescription(report.getDescription());
        this.setReason(report.getReason());
        this.setType(ReportType.PRODUCT);
        this.setReporter(report.getReporter());
        this.setReported(report.getReported());
        this.setCreatedDate(report.getCreatedDate());
        this.setLastModifiedDate(report.getLastModifiedDate());
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REPORTED_PRODUCT",nullable = false)
    private Product product;
}
