package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table
@Entity(name = "PRODUCTS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements OwnableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PRODUCT_ID")
    private UUID id;

    @Column(name = "NAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String name;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 10,max = 20)
    private String description;

    @Column(name = "BRAND",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 5,max = 20)
    private String brand;

    @Column(name = "PRICE",nullable = false)
    private BigDecimal price;

    @Column(name = "MIN_PRICE",nullable = false)
    private BigDecimal minPrice;

    @Column(name = "CONDITION",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCondition condition;

    @Column(name = "VISIBILITY",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductVisibility visibility;

    @Column(name = "STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product",orphanRemoval = true,fetch = FetchType.LAZY)
    public Set<Like> receivedLikes = new HashSet<>();

    @OneToMany(mappedBy = "product",orphanRemoval = true,fetch = FetchType.LAZY)
    public Set<Conversation> conversations = new HashSet<>();

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "CATEGORY_ID",nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "SELLER_ID",nullable = false)
    private User seller;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<ProductImage> productImages = new HashSet<>();

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.seller.getId();
    }
}
