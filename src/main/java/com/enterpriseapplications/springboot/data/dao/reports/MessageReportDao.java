package com.enterpriseapplications.springboot.data.dao.reports;

import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageReportDao extends JpaRepository<MessageReport, UUID> {

    @Query("select m from MessageReport m where m.message.id = :requiredID")
    Page<MessageReport> getMessageReports(@Param("requiredID") UUID requiredID, Pageable pageable);
    @Query("select m from MessageReport m where m.message.id = :messageID and m.reporter.id = :userID")
    Optional<MessageReport> getMessageReport(@Param("messageID") UUID messageID, @Param("userID") UUID userID);
}
