package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messageReports")
@RequiredArgsConstructor
public class MessageReportController {

    private final MessageReportService messageReportService;


    @GetMapping("{messageID}")
    public ResponseEntity<PagedModel<MessageReportDto>> getMessageReports(@PathVariable("messageID") Long messageID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageReportDto> reports = this.messageReportService.getMessageReports(messageID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @PostMapping("{messageID}")
    public ResponseEntity<MessageReportDto> createMessageReport(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("messageID") @PositiveOrZero Long messageID) {
        return ResponseEntity.ok(this.messageReportService.createMessageReport(createReportDto,messageID));
    }

    @PutMapping
    public ResponseEntity<MessageReportDto> updateMessageReport(@RequestBody @Valid UpdateReportDto updateReportDto) {
        return ResponseEntity.ok(this.messageReportService.updateMessageReport(updateReportDto));
    }

    @DeleteMapping("{messageReportID}")
    public ResponseEntity<Void> deleteMessageReport(@PathVariable("messageReportID") Long messageReportID) {
        this.messageReportService.deleteMessageReport(messageReportID);
        return ResponseEntity.noContent().build();
    }
}
