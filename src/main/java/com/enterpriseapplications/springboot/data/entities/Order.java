package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.entities.enums.OrderStatus;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "ORDERS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUYER",nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ADDRESS_ID",nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PAYMENT_METHOD_ID",nullable = false)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PRODUCT_ID",nullable = false)
    private Product product;

    @Column(name = "STATUS",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "DELIVERY_DATE",nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "PRICE",nullable = false)
    private BigDecimal price;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return buyer.getId();
    }
}
