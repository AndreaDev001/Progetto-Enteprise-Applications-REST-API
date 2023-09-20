package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BanServiceImp implements BanService {

    private final BanDao banDao;
    private final UserDao userDao;
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
    @Transactional
    public BanDto createBan(CreateBanDto createBanDto) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User bannedUser = this.userDao.findById(createBanDto.getBannedID()).orElseThrow();
        Optional<Ban> banOptional = this.banDao.findBan(createBanDto.getBannedID());
        if(requiredUser.getId().equals(createBanDto.getBannedID()))
            throw new InvalidFormat("error.bans.invalidBanner");
        if(banOptional.isPresent())
            throw new InvalidFormat("error.bans.alreadyBanned");
        Ban ban = new Ban();
        ban.setBanned(bannedUser);
        ban.setBanner(requiredUser);
        ban.setReason(createBanDto.getReason());
        ban.setExpired(false);
        ban.setExpirationDate(createBanDto.getExpirationDate());
        this.banDao.save(ban);
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    @Transactional
    public BanDto updateBan(UpdateBanDto updateBanDto) {
        Ban requiredBan = this.banDao.findBan(updateBanDto.getBannedID()).orElseThrow();
        if(updateBanDto.getReason() != null)
            requiredBan.setReason(updateBanDto.getReason());
        if(updateBanDto.getDescription() != null)
            requiredBan.setDescription(updateBanDto.getDescription());
        if(updateBanDto.getExpirationDate() != null)
            requiredBan.setExpirationDate(updateBanDto.getExpirationDate());
        this.banDao.save(requiredBan);
        return this.modelMapper.map(requiredBan,BanDto.class);
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
