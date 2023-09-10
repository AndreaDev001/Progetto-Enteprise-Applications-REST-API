package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BanServiceImp implements BanService {

    private final BanDao banDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<BanDto> getCreatedBans(Long userID, Pageable pageable) {
        Page<Ban> bans = this.banDao.getCreatedBans(userID,pageable);
        return new PageImpl<>(bans.stream().map(ban -> this.modelMapper.map(ban,BanDto.class)).collect(Collectors.toList()),pageable,bans.getTotalElements());
    }
    @Override
    public Page<BanDto> getReceivedBans(Long userID, Pageable pageable) {
        Page<Ban> bans = this.banDao.getReceivedBans(userID,pageable);
        return new PageImpl<>(bans.stream().map(ban -> this.modelMapper.map(ban,BanDto.class)).collect(Collectors.toList()),pageable,bans.getTotalElements());
    }
    @Override
    public BanDto getCurrentBan(Long userID) {
        Ban ban = this.banDao.findBan(userID).orElseThrow();
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    public Page<BanDto> getBansByReason(ReportReason reason, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByReason(reason,pageable);
        return new PageImpl<>(bans.stream().map(ban -> this.modelMapper.map(ban,BanDto.class)).collect(Collectors.toList()),pageable,bans.getTotalElements());
    }


    @Override
    public void deleteBan(Long id) {
        this.banDao.findById(id).orElseThrow();
        this.banDao.deleteById(id);
    }
}
