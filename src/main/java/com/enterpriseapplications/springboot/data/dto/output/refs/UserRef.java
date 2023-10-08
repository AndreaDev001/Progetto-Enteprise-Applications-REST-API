package com.enterpriseapplications.springboot.data.dto.output.refs;


import com.enterpriseapplications.springboot.controllers.UserController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
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

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(UserController.class).getUserDetails(id)).withRel("details"));
    }
}
