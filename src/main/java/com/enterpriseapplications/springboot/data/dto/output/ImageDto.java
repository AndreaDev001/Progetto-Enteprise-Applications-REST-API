package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.controllers.ImageController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto extends GenericOutput<ImageDto>
{
    private Long id;
    private String name;
    private String type;
    private LocalDate createdDate;
    @JsonIgnore
    private byte[] image;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ImageController.class).getImage(name)).withRel("imageURL"));
    }
}
