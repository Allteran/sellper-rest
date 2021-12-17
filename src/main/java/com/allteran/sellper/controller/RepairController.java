package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/repair")
public class RepairController {
    private final RepairService repairService;

    @Autowired
    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @RequestMapping
    public List<RepairOrder> getAllOrders() {
        return repairService.getAllOrders();
    }
}
