package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.entities.enums.OrderStatus;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "ORDERS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUYER")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ADDRESS")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PAYMENT_METHOD")
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "STATUS",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "DELIVERY_DATE",nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "PRICE",unique = false)
    private BigDecimal price;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return buyer.getId();
    }
}
