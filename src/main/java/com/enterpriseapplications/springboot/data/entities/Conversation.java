package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.entities.interfaces.MultiOwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Table(name = "CONVERSATIONS",uniqueConstraints = {@UniqueConstraint(columnNames = {"FIRST_ID","SECOND_ID","PRODUCT_ID"})})
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conversation implements MultiOwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "FIRST_ID")
    private User first;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_ID")
    private User second;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;


    @Override
    public List<UUID> getOwners() {
        return List.of(first.getId(),second.getId());
    }
}
