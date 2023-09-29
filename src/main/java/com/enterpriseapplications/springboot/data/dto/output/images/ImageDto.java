package com.enterpriseapplications.springboot.data.dto.output.images;


import com.enterpriseapplications.springboot.controllers.images.ImageController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto extends GenericOutput<ImageDto>
{
    protected UUID id;
    protected String name;
    protected String type;
    protected LocalDate createdDate;
    protected ImageOwner owner;

    @JsonIgnore
    protected byte[] image;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ImageController.class).getImage(id)).withRel("imageURL"));
    }
}
