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
    private UserRef first;
    private UserRef second;
    private ProductRef productRef;

    public ConversationRef(Conversation conversation) {
        this.id = conversation.getId();
        this.productRef = new ProductRef(conversation.getProduct());
        this.first = new UserRef(conversation.getFirst());
        this.second = new UserRef(conversation.getSecond());
    }

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ConversationController.class).getConversation(id)).withRel("details").withName("details"));
    }
}
