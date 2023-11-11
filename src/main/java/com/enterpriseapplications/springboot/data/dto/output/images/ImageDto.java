package com.enterpriseapplications.springboot.data.dto.output.images;


import com.enterpriseapplications.springboot.controllers.images.ImageController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.enums.ImageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto extends GenericOutput<ImageDto>
{
    protected UUID id;
    protected ImageType type;
    protected LocalDate createdDate;

    @JsonIgnore
    protected byte[] image;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ImageController.class).getImage(id)).withRel("imageURL"));
    }
}
