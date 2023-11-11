package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface ConversationDao extends JpaRepository<Conversation, UUID>
{
    @Query("select c from Conversation c where c.starter.id = :requiredID")
    List<Conversation> getConversationByStarter(@Param("requiredID") UUID id);
    @Query("select c from Conversation c where c.starter.id = :starterID and c.product.id = :productID")
    Optional<Conversation> getConversation(@Param("starterID") UUID starter, @Param("productID") UUID productID);
    @Query("select c from Conversation c where c.starter.id = :requiredID or c.product.seller.id = :requiredID")
    List<Conversation> getConversations(@Param("requiredID") UUID id);
    @Query("select c from Conversation c where c.product.id = :requiredID")
    Page<Conversation> getConversationByProduct(@Param("requiredID") UUID ID, Pageable pageable);
}
