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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "REVIEWS",uniqueConstraints = {@UniqueConstraint(columnNames = {"WRITER_ID","RECEIVER_ID"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "TEXT",nullable = false)
    @Convert(converter = TrimConverter.class)
    private String text;

    @OneToOne(mappedBy = "review",cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "REPLY_ID",nullable = false)
    private Reply reply;

    @Column(name = "RATING",nullable = false)
    private Integer rating;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID",nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID",nullable = false)
    private User receiver;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    @Override
    public UUID getOwnerID() {
        return this.writer.getId();
    }
}
