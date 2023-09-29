package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "BANS")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ban
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANNER",unique = false)
    private User banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANNED",unique = false)
    private User banned;

    @Column(name = "DESCRIPTION",unique = false)
    private String description;

    @Column(name = "REASON",unique = false)
    @Enumerated(EnumType.STRING)
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
