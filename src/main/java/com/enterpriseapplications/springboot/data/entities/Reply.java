package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "REPLIES")
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "WRITER")
    private User writer;

    @Column(name = "TEXT",unique = false)
    @Convert(converter = TrimConverter.class)
    private String text;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.writer.getId();
    }
}
