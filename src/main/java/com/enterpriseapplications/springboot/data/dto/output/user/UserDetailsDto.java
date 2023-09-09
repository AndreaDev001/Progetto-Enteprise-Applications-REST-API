package com.enterpriseapplications.springboot.data.dto.output.user;

import com.enterpriseapplications.springboot.controllers.FollowController;
import com.enterpriseapplications.springboot.controllers.reports.ReportController;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

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
    private LocalDate createdDate;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(FollowController.class).getFollowers(id,new PaginationRequest(0,10))).withRel("followers").withName("followers"));
        this.add(linkTo(methodOn(FollowController.class).getFollowed(id,new PaginationRequest(0,10))).withRel("followed").withName("followed"));
        this.add(linkTo(methodOn(ReportController.class).getReportsByReporter(id,new PaginationRequest(0,10))).withRel("created_reports").withName("created reports"));
        this.add(linkTo(methodOn(ReportController.class).getReportsByReported(id,new PaginationRequest(0,10))).withRel("received_reports").withName("received reports"));
    }
}
