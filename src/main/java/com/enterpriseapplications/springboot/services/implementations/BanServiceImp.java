package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.BanSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class BanServiceImp implements BanService {

    private final BanDao banDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Ban,BanDto> modelAssembler;
    private final PagedResourcesAssembler<Ban> pagedResourcesAssembler;

    public BanServiceImp(BanDao banDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Ban> pagedResourcesAssembler) {
        this.banDao = banDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Ban.class,BanDto.class,modelMapper);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_ALL_BANS)
    public PagedModel<BanDto> getBans(Pageable pageable) {
        Page<Ban> bans = this.banDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getCreatedBans(UUID userID, Pageable pageable) {
        Page<Ban> bans = this.banDao.getCreatedBans(userID,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }
    @Override
    public PagedModel<BanDto> getReceivedBans(UUID userID, Pageable pageable) {
        Page<Ban> bans = this.banDao.getReceivedBans(userID,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }
    @Override
    public BanDto getCurrentBan(UUID userID) {
        Ban ban = this.banDao.findBan(userID).orElseThrow();
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    @CacheEvict(value = CacheConfig.CACHE_SEARCH_BANS,allEntries = true)
    @Transactional
    public BanDto createBan(CreateBanDto createBanDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
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
    @CacheEvict(value = CacheConfig.CACHE_SEARCH_BANS,allEntries = true)
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
    public PagedModel<BanDto> getBansByReason(ReportReason reason, Pageable pageable) {
        Page<Ban> bans = this.banDao.getBansByReason(reason,pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public PagedModel<BanDto> getBansBySpec(Specification<Ban> specification, Pageable pageable) {
        Page<Ban> bans = this.banDao.findAll(specification, pageable);
        return this.pagedResourcesAssembler.toModel(bans,modelAssembler);
    }

    @Override
    public BanSpecifications.OrderType[] getOrderTypes() {
        return BanSpecifications.OrderType.values();
    }

    @Override
    public BanDto getBan(UUID banID) {
        Ban ban = this.banDao.findById(banID).orElseThrow();
        return this.modelMapper.map(ban,BanDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SEARCH_BANS,allEntries = true)
    public void deleteBan(UUID id) {
        this.banDao.findById(id).orElseThrow();
        this.banDao.deleteById(id);
    }
}
