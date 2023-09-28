package com.enterpriseapplications.springboot.data.entities;


import com.enterpriseapplications.springboot.data.converters.TrimConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "REVIEWS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TEXT",unique = false)
    @Convert(converter = TrimConverter.class)
    private String text;

    @OneToOne(optional = true)
    private Reply reply;

    @Column(name = "RATING",unique = false)
    private Integer rating;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "WRITER")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER")
    private User receiver;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDate lastModifiedDate;
}
