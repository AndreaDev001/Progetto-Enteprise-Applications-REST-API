package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface ConversationDao extends JpaRepository<Conversation, UUID>
{
    @Query("select c from Conversation c where c.first.id = :requiredID")
    List<Conversation> getConversationByFirst(@Param("requiredID") UUID id);

    @Query("select c from Conversation c where c.second.id = :requiredID")
    List<Conversation> getConversationBySecond(@Param("requiredID") UUID id);

    @Query("select c from Conversation c where c.first.id = :firstID and c.second.id = :secondID")
    List<Conversation> getConversations(@Param("firstID") UUID first,@Param("secondID") UUID second);

    @Query("select c from Conversation c where c.first.id = :requiredID or c.second.id = :requiredID")
    List<Conversation> getConversations(@Param("requiredID") UUID id);

    @Query("select c from Conversation c where c.product.id = :requiredID or c.product.id = :requiredID")
    Page<Conversation> getConversationByProduct(@Param("requiredID") UUID ID, Pageable pageable);
}
