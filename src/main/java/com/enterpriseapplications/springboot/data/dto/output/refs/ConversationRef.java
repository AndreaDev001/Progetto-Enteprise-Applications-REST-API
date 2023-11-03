package com.enterpriseapplications.springboot.data.dto.output.refs;


import com.enterpriseapplications.springboot.controllers.ConversationController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.Conversation;
import lombok.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationRef extends GenericOutput<ConversationRef>
{
    private UUID id;
    private UserRef starter;
    private ProductRef product;

    public ConversationRef(Conversation conversation) {
        this.id = conversation.getId();
        this.product = new ProductRef(conversation.getProduct());
        this.starter = new UserRef(conversation.getStarter());
    }

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ConversationController.class).getConversation(id)).withRel("details").withName("details"));
    }
}
