package com.enterpriseapplications.springboot.data.entities.images;


import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.entities.enums.ImageType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "IMAGES")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "TYPE",unique = false,nullable = false)
    private ImageType type;

    @Column(name = "IMAGE",unique = false,nullable = false)
    protected byte[] image;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false,nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false,nullable = false)
    private LocalDate lastModifiedDate;
}
