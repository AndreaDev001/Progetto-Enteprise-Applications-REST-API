package com.enterpriseapplications.springboot.data.dto.output.user;

import com.enterpriseapplications.springboot.HateoasUtils;
import com.enterpriseapplications.springboot.controllers.*;
import com.enterpriseapplications.springboot.controllers.images.UserImageController;
import com.enterpriseapplications.springboot.controllers.reports.ReportController;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import lombok.*;

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
    private UserVisibility userVisibility;
    private int amountOfFollowers = 0;
    private int amountOfFollowed = 0;
    private int amountOfProducts = 0;
    private int amountOfOrders = 0;
    private LocalDate createdDate;

    @Override
    @SneakyThrows
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        String paginationQuery = HateoasUtils.convert(paginationRequest);
        System.out.println(paginationQuery);
        this.add(linkTo(methodOn(FollowController.class).getFollowers(id,paginationRequest)).slash(paginationQuery).withRel("followers").withName("followers"));
        this.add(linkTo(methodOn(FollowController.class).getFollowed(id,paginationRequest)).slash(paginationQuery).withRel("followed").withName("followed"));
        this.add(linkTo(methodOn(ReportController.class).getReportsByReporter(id,paginationRequest)).slash(paginationQuery).withRel("created_reports").withName("createdReports"));
        this.add(linkTo(methodOn(ReportController.class).getReportsByReported(id,paginationRequest)).slash(paginationQuery).withRel("received_reports").withName("receivedReports"));
        this.add(linkTo(methodOn(BanController.class).getCreatedBans(id,paginationRequest)).slash(paginationQuery).withRel("created_bans").withName("createdBans"));
        this.add(linkTo(methodOn(BanController.class).getReceivedBans(id,paginationRequest)).slash(paginationQuery).withRel("received_bans").withName("receivedBans"));
        this.add(linkTo(methodOn(OrderController.class).getOrders(id,paginationRequest)).slash(paginationQuery).withRel("orders").withName("orders"));
        this.add(linkTo(methodOn(ReviewController.class).findAllWrittenReviews(id,paginationRequest)).slash(paginationQuery).withRel("written_reviews").withName("writtenReviews"));
        this.add(linkTo(methodOn(ReviewController.class).findAllReceivedReviews(id,paginationRequest)).slash(paginationQuery).withRel("received_reviews").withName("receivedReviews"));
        this.add(linkTo(methodOn(ReplyController.class).getReplies(id,paginationRequest)).slash(paginationQuery).withRel("replies").withName("replies"));
        this.add(linkTo(methodOn(UserImageController.class).getUserImage(id)).withRel("image").withName("image"));
    }
}
