package com.enterpriseapplications.springboot.services.interfaces;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface BanService
{
    PagedModel<BanDto> getCreatedBans(Long userID, Pageable pageable);
    PagedModel<BanDto> getReceivedBans(Long userID,Pageable pageable);
    PagedModel<BanDto> getBansByReason(ReportReason reason,Pageable pageable);
    BanDto getCurrentBan(Long userID);
    BanDto createBan(CreateBanDto createBanDto);
    BanDto updateBan(UpdateBanDto updateBanDto);
    void deleteBan(Long id);
}
