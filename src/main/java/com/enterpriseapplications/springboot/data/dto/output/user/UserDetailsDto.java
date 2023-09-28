package com.enterpriseapplications.springboot.data.dto.output.user;

import com.enterpriseapplications.springboot.controllers.BanController;
import com.enterpriseapplications.springboot.controllers.FollowController;
import com.enterpriseapplications.springboot.controllers.OrderController;
import com.enterpriseapplications.springboot.controllers.images.UserImageController;
import com.enterpriseapplications.springboot.controllers.reports.ReportController;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto extends GenericOutput<UserDetailsDto> {

    private Long id;
    private String email;
    private String username;
    private String name;
    private String surname;
    private String description;
    private int amountOfFollowers = 0;
    private int amountOfFollowed = 0;
    private int amountOfProducts = 0;
    private int amountOfOrders = 0;
    private LocalDate createdDate;

    @Override
    public void addLinks(Object... params) {
        Map<String,Object> paginationParameters = new HashMap<>();
        paginationParameters.put("page",0);
        paginationParameters.put("pageSize",20);
        this.add(linkTo(methodOn(FollowController.class,paginationParameters).getFollowers(id,new PaginationRequest(0,20))).withRel("followers").withName("followers"));
        this.add(linkTo(methodOn(FollowController.class,paginationParameters).getFollowed(id,new PaginationRequest(0,20))).withRel("followed").withName("followed"));
        this.add(linkTo(methodOn(ReportController.class,paginationParameters).getReportsByReporter(id,new PaginationRequest(0,20))).withRel("created_reports").withName("createdReports"));
        this.add(linkTo(methodOn(ReportController.class,paginationParameters).getReportsByReported(id,new PaginationRequest(0,20))).withRel("received_reports").withName("receivedReports"));
        this.add(linkTo(methodOn(BanController.class,paginationParameters).getCreatedBans(id,new PaginationRequest(0,20))).withRel("created_bans").withName("createdBans"));
        this.add(linkTo(methodOn(BanController.class,paginationParameters).getReceivedBans(id, new PaginationRequest(0,20))).withRel("received_bans").withName("receivedBans"));
        this.add(linkTo(methodOn(OrderController.class,paginationParameters).getOrders(id, new PaginationRequest(0,20))).withRel("orders").withName("orders"));
        this.add(linkTo(methodOn(UserImageController.class).getUserImage(id)).withRel("image").withName("image"));
    }
}
