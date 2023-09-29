package com.enterpriseapplications.springboot.data.entities.images;


import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "NAME",unique = false)
    protected String name;

    @Column(name = "TYPE",unique = false)
    protected String type;

    @Column(name = "OWNER",unique = false)
    @Enumerated(EnumType.STRING)
    protected ImageOwner owner;

    @Lob
    @Column(name = "IMAGE",unique = false,nullable = false)
    protected byte[] image;

    @CreatedDate
    @Column(name = "CREATED_DATE",unique = false)
    protected LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",unique = false)
    protected LocalDate lastModifiedDate;

    protected Image(MultipartFile multipartFile) throws IOException {
        this.setName(multipartFile.getOriginalFilename());
        this.setType(multipartFile.getContentType());
        this.setImage(ImageUtils.compressImage(multipartFile.getBytes()));
    }
}
