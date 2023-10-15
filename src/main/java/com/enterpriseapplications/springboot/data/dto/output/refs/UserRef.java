package com.enterpriseapplications.springboot.data.dto.output.refs;


import com.enterpriseapplications.springboot.controllers.UserController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import lombok.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRef extends GenericOutput<UserRef>
{
    private UUID id;
    private String username;
    private String name;
    private String surname;
    private Long rating;
    private Gender gender;


    public UserRef(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
        this.setRating(user.getRating());
        this.setGender(user.getGender());
        this.addLinks();
    }

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(UserController.class).getUserDetails(id)).withRel("details"));
    }
}
