package com.enterpriseapplications.springboot.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Table(name = "ORDERS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER")
    private User buyer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "ID")
    private Product product;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;
}
