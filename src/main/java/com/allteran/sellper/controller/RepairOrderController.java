package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.service.ExcelService;
import com.allteran.sellper.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/repair/order")
public class RepairOrderController {
    private final RepairOrderService repairService;
    private final ExcelService sheetService;

    @Autowired
    public RepairOrderController(RepairOrderService repairService, ExcelService sheetService) {
        this.repairService = repairService;
        this.sheetService = sheetService;
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

    @PostMapping("new/download/acceptance_cert")
//    @CrossOrigin TODO: uncomment this and try again
//    @ResponseBody
    public ResponseEntity<Resource> downloadAcceptanceCertificate(
            @RequestBody RepairOrder order) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=ACCEPTANCE_CERTIFICATE.xlsx");
        ByteArrayInputStream certificate = sheetService.generateAcceptanceCertificate(order);

        InputStreamResource resource = new InputStreamResource(certificate);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(certificate.read())
                .body(resource);
    }
}
