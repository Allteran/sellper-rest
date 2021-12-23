package com.allteran.sellper.controller;

import com.allteran.sellper.domain.RepairStatus;
import com.allteran.sellper.service.RepairStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/repair/status")
public class RepairStatusController {
    private final RepairStatusService statusService;

    @Autowired
    public RepairStatusController(RepairStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public List<RepairStatus> getAll() {
        return statusService.getAll();
    }

    @GetMapping("{id}")
    public RepairStatus getOne(@PathVariable("id") RepairStatus status){
        return status;
    }

    @PostMapping
    public RepairStatus create(@RequestBody RepairStatus status) {
        return statusService.create(status);
    }

    @PutMapping("{id}")
    public RepairStatus update(@PathVariable("id") RepairStatus statusFromDb,
                               @RequestBody RepairStatus status) {
        return statusService.update(statusFromDb, status);
    }

    @DeleteMapping("id")
    public void delete(@PathVariable("id") RepairStatus status) {
        statusService.delete(status);
    }
}
