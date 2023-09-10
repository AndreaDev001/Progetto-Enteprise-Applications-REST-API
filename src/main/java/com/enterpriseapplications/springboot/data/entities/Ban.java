package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Table(name = "BANS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ban
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANNER",unique = false)
    private User banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANNED",unique = false)
    private User banned;

    @Column(name = "REASON",unique = false)
    private ReportReason reason;

    @Column(name = "EXPIRATION_DATE",unique = false)
    private LocalDate expirationDate;

    @Column(name = "EXPIRED",unique = false)
    private boolean expired;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;
}