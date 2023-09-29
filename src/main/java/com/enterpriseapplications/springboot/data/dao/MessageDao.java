package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MessageDao extends JpaRepository<Message, UUID>
{
    @Query("select m from Message m where m.sender.id = :requiredID order by m.createdDate desc")
    Page<Message> getSentMessages(@Param("requiredID") UUID senderID,Pageable pageable);

    @Query("select m from Message m where m.receiver.id = :requiredID order by m.createdDate desc")
    Page<Message> getReceivedMessages(@Param("requiredID") UUID receiverID, Pageable pageable);

    @Query("select m from Message m where m.sender.id = :requiredSenderID and m.receiver.id = :requiredReceiverID order by m.createdDate desc")
    Page<Message> getMessagesBetween(@Param("requiredSenderID") UUID senderID,@Param("requiredReceiverID") UUID receiverID,Pageable pageable);
}
