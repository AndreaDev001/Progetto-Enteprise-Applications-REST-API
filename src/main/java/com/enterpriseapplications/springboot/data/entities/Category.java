package com.enterpriseapplications.springboot.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "CATEGORIES")
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRIMARY",unique = false)
    private String primary;

    @Column(name = "SECONDARY",unique = false)
    private String secondary;

    @Column(name = "TERTIARY",unique = false)
    private String tertiary;
}
