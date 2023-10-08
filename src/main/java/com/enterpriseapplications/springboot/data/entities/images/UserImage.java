package com.enterpriseapplications.springboot.data.entities.images;

import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
        this.setName(image.getName());
        this.setType(image.getType());
        this.setOwner(ImageOwner.USER);
        this.setImage(image.getImage());
        this.setCreatedDate(image.getCreatedDate());
        this.setLastModifiedDate(image.getLastModifiedDate());
    }
    public UserImage(User user, MultipartFile multipartFile) throws IOException {
        super(multipartFile);
        this.setOwner(ImageOwner.USER);
        this.setUser(user);
    }
    @OneToOne(mappedBy = "userImage")
    @JoinColumn(name = "USER_ID")
    private User user;
}
