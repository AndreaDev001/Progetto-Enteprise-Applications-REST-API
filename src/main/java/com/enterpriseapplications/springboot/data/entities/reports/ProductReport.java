package com.enterpriseapplications.springboot.data.entities.reports;


import com.enterpriseapplications.springboot.data.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Table(name = "PRODUCT_REPORTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReport extends Report
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REPORTED_PRODUCT")
    private Product product;
}
