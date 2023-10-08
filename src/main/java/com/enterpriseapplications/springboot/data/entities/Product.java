package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table
@Entity(name = "PRODUCTS")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements OwnableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME",unique = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "DESCRIPTION",unique = false)
    @Convert(converter = TrimConverter.class)
    private String description;

    @Column(name = "BRAND",unique = false)
    @Convert(converter = TrimConverter.class)
    private String brand;

    @Column(name = "PRICE",unique = false)
    private BigDecimal price;

    @Column(name = "MIN_PRICE",unique = false)
    private BigDecimal minPrice;

    @Column(name = "CONDITION",unique = false)
    @Enumerated(EnumType.STRING)
    private ProductCondition condition;

    @Column(name = "VISIBILITY",unique = false)
    @Enumerated(EnumType.STRING)
    private ProductVisibility visibility;

    @ManyToMany
    private Set<User> likes = new HashSet<>();

    @OneToOne(mappedBy = "product")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User seller;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<ProductImage> productImages = new HashSet<>();

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.seller.getId();
    }
}
