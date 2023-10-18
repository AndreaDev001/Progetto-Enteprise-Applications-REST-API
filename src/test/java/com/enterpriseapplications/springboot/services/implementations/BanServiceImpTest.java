package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.BanSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class BanServiceImpTest extends GenericTestImp<Ban,BanDto> {

    private BanServiceImp banServiceImp;

    @Mock
    private BanDao banDao;
    @Mock
    private UserDao userDao;

    @Override
    protected void init() {
        super.init();
        banServiceImp = new BanServiceImp(banDao,userDao,modelMapper,pagedResourcesAssembler);
        User banner = User.builder().id(UUID.randomUUID()).build();
        User banned = User.builder().id(UUID.randomUUID()).build();
        firstElement = Ban.builder().id(UUID.randomUUID()).expired(false).banner(banner).banned(banned).description("description").reason(ReportReason.RACISM).build();
        secondElement = Ban.builder().id(UUID.randomUUID()).expired(false).banner(banned).banned(banner).description("description").reason(ReportReason.NUDITY).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    public boolean valid(Ban ban, BanDto banDto) {
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
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.banDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<BanDto> bans = this.banServiceImp.getBans(pageRequest);
        Assert.assertTrue(compare(elements,bans.getContent().stream().toList()));
        Assert.assertTrue(validPage(bans,20,0,1,2));
    }

    @Test
    void getCreatedBans() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.banDao.getCreatedBans(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<BanDto> bans = this.banServiceImp.getCreatedBans(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,bans.getContent().stream().toList()));
        Assert.assertTrue(validPage(bans,20,0,1,2));
    }

    @Test
    void getCurrentBan() {
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        given(this.banDao.findBan(firstUser.getId())).willReturn(Optional.of(firstElement));
        given(this.banDao.findBan(secondUser.getId())).willReturn(Optional.of(secondElement));
        BanDto firstBan = this.banServiceImp.getCurrentBan(firstUser.getId());
        BanDto secondBan = this.banServiceImp.getCurrentBan(secondUser.getId());
        Assert.assertTrue(valid(firstElement,firstBan));
        Assert.assertTrue(valid(secondElement,secondBan));
    }

    @Test
    void getBansByReason() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.banDao.getBansByReason(ReportReason.RACISM,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<BanDto> bans = this.banServiceImp.getBansByReason(ReportReason.RACISM,pageRequest);
        Assert.assertTrue(compare(elements,bans.getContent().stream().toList()));
        Assert.assertTrue(validPage(bans,20,0,1,2));
    }

    @Test
    void getBansBySpec() {
    }

    @Test
    void getSimilarBans() {
        Ban ban = Ban.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        BanSpecifications.Filter filter = new BanSpecifications.Filter(ban);
        given(this.banDao.findById(ban.getId())).willReturn(Optional.of(ban));
        given(this.banDao.findAll(BanSpecifications.withFilter(filter),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<BanDto> bans = this.banServiceImp.getSimilarBans(ban.getId(),pageRequest);
        Assert.assertTrue(compare(elements,bans.getContent().stream().toList()));
        Assert.assertTrue(validPage(bans,20,0,1,2));
    }

    @Test
    void getBan() {
        given(this.banDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.banDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        BanDto firstBan = this.banServiceImp.getBan(this.firstElement.getId());
        BanDto secondBan = this.banServiceImp.getBan(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstBan));
        Assert.assertTrue(valid(this.secondElement,secondBan));
    }

    @Test
    void createBan() {
        User user = User.builder().id(UUID.randomUUID()).build();
        CreateBanDto createBanDto = CreateBanDto.builder().bannedID(user.getId()).reason(ReportReason.RACISM).expirationDate(LocalDate.now()).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.userDao.findById(user.getId())).willReturn(Optional.of(user));
        given(this.banDao.save(any(Ban.class))).willReturn(firstElement);
        BanDto banDto = this.banServiceImp.createBan(createBanDto);
        Assert.assertTrue(valid(firstElement,banDto));
    }
}