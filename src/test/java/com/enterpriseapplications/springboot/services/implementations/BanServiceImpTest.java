package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class BanServiceImpTest {

    private BanServiceImp banServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Ban> pagedResourcesAssembler;
    private Ban firstBan;
    private Ban secondBan;

    @Mock
    private BanDao banDao;
    @Mock
    private UserDao userDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        banServiceImp = new BanServiceImp(banDao,userDao,modelMapper,pagedResourcesAssembler);
        User banner = User.builder().id(UUID.randomUUID()).build();
        User banned = User.builder().id(UUID.randomUUID()).build();
        firstBan = Ban.builder().id(UUID.randomUUID()).expired(false).banner(banner).banned(banned).description("description").reason(ReportReason.RACISM).build();
        secondBan = Ban.builder().id(UUID.randomUUID()).expired(false).banner(banned).banned(banner).description("description").reason(ReportReason.NUDITY).build();
    }

    boolean valid(Ban ban, BanDto banDto) {
        Assert.assertNotNull(banDto);
        Assert.assertEquals(ban.getId(),banDto.getId());
        Assert.assertEquals(ban.getDescription(),banDto.getDescription());
        Assert.assertEquals(ban.getReason(),banDto.getReason());
        Assert.assertEquals(ban.getExpirationDate(),banDto.getExpirationDate());
        Assert.assertEquals(ban.isExpired(),banDto.isExpired());
        Assert.assertEquals(ban.getBanned().getId(),banDto.getBanned().getId());
        Assert.assertEquals(ban.getBanner().getId(),banDto.getBanner().getId());
        Assert.assertEquals(ban.getCreatedDate(),banDto.getCreatedDate());
        return true;
    }

    @Test
    void getBans() {
    }

    @Test
    void getCreatedBans() {
    }

    @Test
    void getCurrentBan() {
    }

    @Test
    void getBansByReason() {
    }

    @Test
    void getBansBySpec() {
    }

    @Test
    void getBan() {
        given(this.banDao.findById(firstBan.getId())).willReturn(Optional.of(firstBan));
        given(this.banDao.findById(secondBan.getId())).willReturn(Optional.of(secondBan));
        BanDto firstBan = this.banServiceImp.getBan(this.firstBan.getId());
        BanDto secondBan = this.banServiceImp.getBan(this.secondBan.getId());
        Assert.assertTrue(valid(this.firstBan,firstBan));
        Assert.assertTrue(valid(this.secondBan,secondBan));
    }
}