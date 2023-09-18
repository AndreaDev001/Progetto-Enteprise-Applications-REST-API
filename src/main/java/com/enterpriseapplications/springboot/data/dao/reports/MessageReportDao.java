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

@Repository
public interface MessageReportDao extends JpaRepository<MessageReport,Long> {

    @Query("select m from MessageReport m where m.message.id = :requiredID")
    Page<MessageReport> getMessageReports(@Param("requiredID") Long requiredID, Pageable pageable);
}
