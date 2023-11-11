package com.enterpriseapplications.springboot.data.entities;

import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "ADDRESSES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "address")
    private Set<Order> orders = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    private User user;

    @Column(name = "STREET",unique = false,nullable = false)
    private String street;

    @Column(name = "LOCALITY",unique = false,nullable = false)
    private String locality;

    @Column(name = "POSTAL_CODE",unique = false,nullable = false)
    private String postalCode;

    @Column(name = "OWNER_NAME",unique = false,nullable = false)
    private String ownerName;

    @Column(name = "COUNTRY_CODE",unique = false,nullable = false)
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    @Column(name = "CREATED_DATE",unique = false,nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "LAST_MODIFIED_DATE",unique = false,nullable = false)
    @LastModifiedDate
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return user.getId();
    }
}
