package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.BanSpecifications;
import com.enterpriseapplications.springboot.data.dao.specifications.SpecificationsUtils;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BanServiceImp extends GenericServiceImp<Ban,BanDto> implements BanService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss");
    private final BanDao banDao;
    private final UserDao userDao;

    public BanServiceImp(BanDao banDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Ban> pagedResourcesAssembler) {
        super(modelMapper,Ban.class,BanDto.class,pagedResourcesAssembler);
        this.banDao = banDao;
        this.userDao = userDao;
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
    @CacheEvict(value = {CacheConfig.CACHE_ALL_BANS,CacheConfig.CACHE_SEARCH_BANS},allEntries = true)
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
        return this.modelMapper.map(this.banDao.save(ban),BanDto.class);
    }

    @Override
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_BANS,CacheConfig.CACHE_ALL_BANS},allEntries = true)
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
    public PagedModel<BanDto> getSimilarBans(UUID banID, Pageable pageable) {
        Ban requiredBan = this.banDao.findById(banID).orElseThrow();
        BanSpecifications.Filter filter = new BanSpecifications.Filter(requiredBan);
        Page<Ban> bans = this.banDao.findAll(BanSpecifications.withFilter(filter),pageable);
        bans.getContent().removeIf(value -> value.getId().equals(requiredBan.getId()));
        return this.pagedResourcesAssembler.toModel(bans,this.modelAssembler);
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_BANS,CacheConfig.CACHE_ALL_BANS,CacheConfig.CACHE_BANNED_USERS},allEntries = true)
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void handleExpiredBans() {
        List<Ban> expiredBans = this.banDao.getBansByDate(LocalDate.now());
        expiredBans.forEach(ban -> {
            log.info(String.format("Ban [%s] has expired at [%s]"),ban.getId().toString(),dateTimeFormatter.format(LocalDateTime.now()));
            ban.setExpired(true);
            this.banDao.save(ban);
        });
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_BANS,CacheConfig.CACHE_ALL_BANS,CacheConfig.CACHE_BANNED_USERS},allEntries = true)
    @Scheduled(fixedDelay = 5 * 24 * 60 * 60 * 1000,initialDelay = 24 * 60 * 60 * 1000)
    public void deleteExpiredBans() {
        log.info(String.format("Expired bans have been deleted at [%s]",dateTimeFormatter.format(LocalDateTime.now())));
        List<Ban> expiredBans = this.banDao.getExpiredBans(true);
        this.banDao.deleteAll(expiredBans);
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
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_BANS,CacheConfig.CACHE_ALL_BANS,CacheConfig.CACHE_BANNED_USERS},allEntries = true)
    public void deleteBan(UUID id) {
        this.banDao.findById(id).orElseThrow();
        this.banDao.deleteById(id);
    }
}
