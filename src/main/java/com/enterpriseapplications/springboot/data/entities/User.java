package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "EMAIL",unique = false)
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
    @Enumerated(EnumType.STRING)
    private UserVisibility visibility;

    @Column(name = "RATING",unique = false)
    private Long rating;

    @Column(name = "STRIPE_ID",unique = false)
    private String stripeID;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private UserImage userImage;

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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "first",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Conversation> createdConversations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "second",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Conversation> receivedConversations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "receiver",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Message> receivedMessages = new HashSet<>();

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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();
}
