package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
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

    @Column(name = "GENDER",unique = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "DESCRIPTION",unique = false)
    @Convert(converter = TrimConverter.class)
    private String description;

    @Column(name = "VISIBILITY",unique = false)
    private UserVisibility userVisibility;

    @Column(name = "RATING",unique = false)
    private Integer rating;

    @Column(name = "STRIPE_ID",unique = false)
    private String stripeID;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @ManyToMany
    @JoinTable(name = "LIKED_PRODUCTS")
    private Set<Product> likedProducts = new HashSet<>();

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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buyer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Offer> createdOffers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();
}
