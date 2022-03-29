package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.domain.RepairStatus;
import com.allteran.sellper.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/report/repair")
public class RepairReportController {
    private final RepairOrderService repairOrderService;

    @Autowired
    public RepairReportController(RepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    @GetMapping("creation-date/from={from}&to={to}&st={statuses}")
    public List<RepairOrder> findAllByCreationDate(
            @PathVariable("from")String from,
            @PathVariable("to") String to,
            @PathVariable("statuses")List<RepairStatus> statuses) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateFrom = LocalDate.parse(from, dateFormatter).atStartOfDay();
        LocalDateTime dateTo = LocalDate.parse(to, dateFormatter).atStartOfDay();

        return repairOrderService.findAllByCreationDateAndStatus(dateFrom, dateTo, statuses);
    }
}
