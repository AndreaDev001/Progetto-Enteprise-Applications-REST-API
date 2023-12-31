package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "EMAIL",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Email
    private String email;

    @Column(name = "USERNAME",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String username;

    @Column(name = "NAME",nullable = true)
    @Convert(converter = TrimConverter.class)
    private String name;

    @Column(name = "SURNAME",nullable = true)
    @Convert(converter = TrimConverter.class)
    private String surname;

    @Column(name = "GENDER",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "DESCRIPTION",nullable = true)
    @Convert(converter = TrimConverter.class)
    private String description;

    @Column(name = "VISIBILITY",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserVisibility visibility;

    @Column(name = "RATING",nullable = true)
    private Long rating;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private UserImage image;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "seller",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "writer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> writtenReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "receiver",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> receivedReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "writer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Reply> writtenReplies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "followed",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "follower",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Follow> follows = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sender",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "receiver",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Message> receivedMessages = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "starter",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Conversation> createdConversations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Like> createdLikes = new HashSet<>();

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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();
}
