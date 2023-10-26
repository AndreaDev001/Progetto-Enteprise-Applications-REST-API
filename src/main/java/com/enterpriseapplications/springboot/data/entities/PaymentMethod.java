package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "PAYMENT_METHODS",uniqueConstraints = {@UniqueConstraint(columnNames = {"OWNER","NUMBER"})})
@Entity
@EntityListeners({AuditingEntityListener.class,})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethod implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "HOLDER_NAME",unique = false)
    private String holderName;

    @Column(name = "NUMBER",unique = false)
    @Pattern(regexp = "[0-9]{16,19}")
    private String number;

    @Column(name = "BRAND",unique = false)
    private PaymentMethodBrand brand;

    @Column(name = "EXPIRATION_DATE",unique = false)
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER")
    private User user;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.user.getId();
    }
}
