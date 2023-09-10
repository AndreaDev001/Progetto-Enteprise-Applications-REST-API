package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL",unique = true)
    @Convert(converter = TrimConverter.class)
    private String email;

    @Column(name = "USERNAME",unique = true)
    @Convert(converter = TrimConverter.class)
    private String username;

    @Column(name = "NAME",unique = false)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "SURNAME",unique = false)
    @Convert(converter = TrimConverter.class)
    private String surname;

    @Column(name = "DESCRIPTION",unique = false)
    @Convert(converter = TrimConverter.class)
    private String description;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "seller",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "writer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> writtenReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "receiver",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> receivedReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "followed",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Follow> followers;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "follower",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Follow> follows;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sender",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "receiver",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Message> receivedMessages = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "reporter",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Report> createdReports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "reported",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Report> receivedReports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buyer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "banner",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Ban> createdBans = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "banned",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Ban> receivedBans = new HashSet<>();
}
