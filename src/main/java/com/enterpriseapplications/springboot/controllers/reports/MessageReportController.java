package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/messageReports")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class MessageReportController {

    private final MessageReportService messageReportService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<MessageReportDto>> getMessageReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageReportDto> reports = this.messageReportService.getReports(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/messageReport/{userID}/{messageID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<MessageReportDto> getMessageReport(@PathVariable("userID") UUID userID,@PathVariable("messageID") UUID messageID) {
        return ResponseEntity.ok(this.messageReportService.getReport(userID,messageID));
    }

    @GetMapping("/private/{reportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#reportID)")
    public ResponseEntity<MessageReportDto> getMessageReport(@PathVariable("reportID") UUID reportID) {
        return ResponseEntity.ok(this.messageReportService.getReport(reportID));
    }

    @GetMapping("/private/message/{messageID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<MessageReportDto>> getMessageReports(@PathVariable("messageID") UUID messageID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageReportDto> reports = this.messageReportService.getMessageReports(messageID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/private/{messageID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<MessageReportDto> createMessageReport(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("messageID") @PositiveOrZero UUID messageID) {
        return ResponseEntity.ok(this.messageReportService.createMessageReport(createReportDto,messageID));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@messageReportDao,#updateReportDto.reportID)")
    public ResponseEntity<MessageReportDto> updateMessageReport(@RequestBody @Valid UpdateReportDto updateReportDto) {
        return ResponseEntity.ok(this.messageReportService.updateMessageReport(updateReportDto));
    }

    @DeleteMapping("/private/{messageReportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@messageReportDao,#messageReportID)")
    public ResponseEntity<Void> deleteMessageReport(@PathVariable("messageReportID") UUID messageReportID) {
        this.messageReportService.deleteMessageReport(messageReportID);
        return ResponseEntity.noContent().build();
    }
}
