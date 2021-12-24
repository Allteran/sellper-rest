package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/repair/order")
public class RepairOrderController {
    private final RepairOrderService repairService;

    @Autowired
    public RepairOrderController(RepairOrderService repairService) {
        this.repairService = repairService;
    }

    @GetMapping
    public List<RepairOrder> getAllOrders() {
        return repairService.getAllOrders();
    }

    @GetMapping("{id}")
    public RepairOrder getOrder(@PathVariable("id") RepairOrder order) {
        return order;
    }

    @PostMapping
    public RepairOrder createOrder(@RequestBody RepairOrder order) {
        return repairService.createOrder(order);
    }

    @PutMapping("{id}")
    public RepairOrder updateOrder(@PathVariable("id") RepairOrder orderFromDb,
                                   @RequestBody RepairOrder order) {
        return repairService.updateOrder(orderFromDb, order);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable("id") RepairOrder order) {
        repairService.deleteOrder(order);
    }
}
