package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.service.ExcelService;
import com.allteran.sellper.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/repair/order")
public class RepairOrderController {
    private final RepairOrderService repairService;
    private final ExcelService sheetService;
    private ByteArrayInputStream acceptanceCertificate;
    private ByteArrayInputStream repairCertificate;

    @Autowired
    public RepairOrderController(RepairOrderService repairService, ExcelService sheetService) {
        this.repairService = repairService;
        this.sheetService = sheetService;
    }

    @GetMapping
    public List<RepairOrder> getAllOrdersForRegistry() {
        return repairService.getAllForRegistry();
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

    @PostMapping("new/generate/acceptance_cert")
    public void generateAcceptanceCertificate(@RequestBody RepairOrder order) {
        try {
            acceptanceCertificate = sheetService.generateAcceptanceCertificate(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("new/generate/acceptance_cert")
    public HttpEntity<ByteArrayResource> downloadAcceptanceCertificate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ACCEPTANCE_CERTIFICATE.xlsx");

        return new HttpEntity<>(new ByteArrayResource(acceptanceCertificate.readAllBytes()), headers);
    }

    @PostMapping("generate/repair_cert")
    public void generateRepairCertificate(@RequestBody RepairOrder order) {
        RepairOrder orderFromDb = repairService.findById(order.getId());
        RepairOrder updatedOrder = repairService.updateOrder(orderFromDb, order);
        try {
            repairCertificate = sheetService.generateRepairCertificate(updatedOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("generate/repair_cert")
    public HttpEntity<ByteArrayResource> downloadRepairCertificate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=REPAIR_CERTIFICATE.xlsx");

        return new HttpEntity<>(new ByteArrayResource(repairCertificate.readAllBytes()), headers);
    }
}
