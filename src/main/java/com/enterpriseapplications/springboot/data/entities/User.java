package com.enterpriseapplications.springboot.data.entities;


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

    @Column(name = "USERNAME",unique = true)
    private String username;

    @Column(name = "NAME",unique = false)
    private String name;

    @Column(name = "SURNAME",unique = false)
    private String surname;

    @Column(name = "DESCRIPTION",unique = false)
    private String description;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "writer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> writtenReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "writer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> receivedReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "followed",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Follow> followers;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "follower",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Follow> follows;
}
