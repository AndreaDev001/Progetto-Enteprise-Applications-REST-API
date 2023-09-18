package com.enterpriseapplications.springboot.services.interfaces;


import com.enterpriseapplications.springboot.data.dto.input.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface BanService
{
    Page<BanDto> getCreatedBans(Long userID, Pageable pageable);
    Page<BanDto> getReceivedBans(Long userID,Pageable pageable);
    Page<BanDto> getBansByReason(ReportReason reason,Pageable pageable);
    BanDto getCurrentBan(Long userID);
    BanDto createBan(CreateBanDto createBanDto);
    void deleteBan(Long id);
}
