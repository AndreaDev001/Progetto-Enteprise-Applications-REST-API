package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messageReports")
@RequiredArgsConstructor
public class MessageReportController {

    private final MessageReportService messageReportService;


    @GetMapping("{messageID}")
    public ResponseEntity<PaginationResponse<MessageReportDto>> getMessageReports(@PathVariable("messageID") Long messageID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<MessageReportDto> reports = this.messageReportService.getMessageReports(messageID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(reports.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reports.getTotalPages(),reports.getTotalElements()));
    }

    @PostMapping("{messageID}")
    public ResponseEntity<MessageReportDto> createMessageReport(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("messageID") @PositiveOrZero Long messageID) {
        return ResponseEntity.ok(this.messageReportService.createMessageReport(createReportDto,messageID));
    }

    @DeleteMapping("{messageReportID}")
    public ResponseEntity<Void> deleteMessageReport(@PathVariable("messageReportID") Long messageReportID) {
        this.messageReportService.deleteMessageReport(messageReportID);
        return ResponseEntity.noContent().build();
    }
}
