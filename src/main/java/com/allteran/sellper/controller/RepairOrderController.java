package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairOrder;
import com.allteran.sellper.service.ExcelService;
import com.allteran.sellper.service.RepairOrderService;
import org.apache.commons.io.IOUtils;
import org.apache.coyote.Response;
import org.bouncycastle.asn1.cms.MetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/repair/order")
public class RepairOrderController {
    private final Logger logger = LoggerFactory.getLogger(RepairOrderController.class);
    public static final String HOST = "http://localhost:8080";

    private final RepairOrderService repairService;
    private final ExcelService sheetService;
    private ByteArrayInputStream acceptanceCertificate;

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

    @PostMapping("new/generate/acceptance_cert")
    public void generateAcceptanceCertificate(
            @RequestBody RepairOrder order) {
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
//        response.setContentType("application/octet-stream");
//        response.setHeader(
//                "Content-Disposition",
//                "attachment; filename=ACCEPTANCE_CERTIFICATE.xlsx");
//        StreamingResponseBody stream = outputStream -> {
//            try {
//                IOUtils.copy(acceptanceCertificate, response.getOutputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        };
//        logger.info("streaming response {} ", stream);
//        return new ResponseEntity(stream, HttpStatus.OK);
    }
}
