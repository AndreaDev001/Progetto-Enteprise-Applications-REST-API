package com.enterpriseapplications.springboot.data.entities.images;

import com.enterpriseapplications.springboot.data.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Table(name = "USER_IMAGES")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserImage extends Image
{
    public UserImage(Image image) {
        this.setId(image.getId());
        this.setType(image.getType());
        this.setImage(image.getImage());
        this.setCreatedDate(image.getCreatedDate());
        this.setLastModifiedDate(image.getLastModifiedDate());
    }
    public UserImage(User user, MultipartFile multipartFile) throws IOException {
        super(multipartFile);
        this.setUser(user);
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
