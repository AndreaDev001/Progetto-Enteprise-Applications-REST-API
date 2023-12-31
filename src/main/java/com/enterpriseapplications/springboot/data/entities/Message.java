package com.enterpriseapplications.springboot.data.entities;

import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "MESSAGES",uniqueConstraints = {@UniqueConstraint(columnNames = {"SENDER_ID","RECEIVER_ID","CREATED_DATE"})})
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements OwnableEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Convert(converter = TrimConverter.class)
    @Column(name = "TEXT",nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID",nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID",nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Conversation conversation;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDateTime createdDate;

    @Override
    public UUID getOwnerID() {
        return this.getSender().getId();
    }
}
