package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "CATEGORIES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRIMARY_CAT",unique = false)
    @Convert(converter = TrimConverter.class)
    private String primaryCat;

    @Column(name = "SECONDARY_CAT",unique = false)
    @Convert(converter = TrimConverter.class)
    private String secondaryCat;

    @Column(name = "TERTIARY_CAT",unique = false)
    @Convert(converter = TrimConverter.class)
    private String tertiaryCat;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;
}