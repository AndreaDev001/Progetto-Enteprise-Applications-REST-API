package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageDao extends JpaRepository<Message,Long>
{
    @Query("select m from Message m where m.sender.id = :requiredID order by m.createdDate desc")
    Page<Message> getSentMessages(@Param("requiredID") Long senderID,Pageable pageable);

    @Query("select m from Message m where m.receiver.id = :requiredID order by m.createdDate desc")
    Page<Message> getReceivedMessages(@Param("requiredID") Long receiverID, Pageable pageable);

    @Query("select m from Message m where m.sender.id = :requiredSenderID and m.receiver.id = :requiredReceiverID order by m.createdDate desc")
    Page<Message> getMessagesBetween(@Param("requiredSenderID") Long senderID,@Param("requiredReceiverID") Long receiverID,Pageable pageable);
}
