package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
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
    @Column(name = "PRODUCT_ID")
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

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Like> receivedLikes = new HashSet<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Conversation> conversations = new HashSet<>();

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
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
